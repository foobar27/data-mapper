package data.refinery.pipeline;

import data.refinery.conversion.EntityFactory;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;
import data.refinery.utils.AllOrNothingFutureContainer;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

final class GenericPipelineEngine<OutputType extends EntityFieldReadAccessor, OutputBuilderType extends EntityFieldReadWriteAccessor>
        implements PipelineEngine<OutputType, OutputBuilderType> {

    private final PipelineDefinition enrichments;
    private final CalculationFactory calculationFactory;
    private final EntityFactory<OutputType, OutputBuilderType> outputFactory;
    private final Executor executor;

    GenericPipelineEngine(
            PipelineDefinition enrichments,
            CalculationFactory calculationFactory,
            EntityFactory<OutputType, OutputBuilderType> outputFactory,
            Executor executor) {
        this.enrichments = checkNotNull(enrichments);
        this.calculationFactory = checkNotNull(calculationFactory);
        this.outputFactory = outputFactory;
        this.executor = executor;
    }

    public CompletableFuture<OutputType> process(EntityFieldReadAccessor inputEntity) {
        Process process = new Process(inputEntity, enrichments);
        return process.future;
    }

    private final class Process {
        private final CompletableFuture<OutputType> future = new CompletableFuture<>();
        private final OutputBuilderType result = outputFactory.newBuilder();
        private final Set<Field> knownFields;
        private final BitSet enrichmentsTriggered;
        private final BitSet enrichmentsFinished;
        private final AllOrNothingFutureContainer<EntityFieldReadAccessor> pendingFutures =
                new AllOrNothingFutureContainer<>(
                        future::completeExceptionally,
                        executor);

        Process(EntityFieldReadAccessor inputEntity, PipelineDefinition enrichments) {
            this.knownFields = new HashSet<>(enrichments.getFixedSchema().getFields());
            this.enrichmentsTriggered = new BitSet(enrichments.getEnrichments().size());
            this.enrichmentsFinished = new BitSet(enrichments.getEnrichments().size());
            inputEntity.filterFields(knownFields).mergeInto(result);
            progress();
        }

        synchronized void progress() {
            int index = 0;
            for (Enrichment enrichment : enrichments.getEnrichments()) {
                final int enrichmentIndex = index;
                if (enrichmentsTriggered.get(enrichmentIndex)) {
                    continue;
                }
                enrichmentsTriggered.set(enrichmentIndex);
                if (knownFields.containsAll(enrichment.getMappedCalculation().getMapping().getLeftMapping().getMapping().keySet())) {
                    // All dependencies satisfied
                    pendingFutures.add(() ->
                            enrichment.apply(result, calculationFactory)
                                    // Recursion!
                                    .thenApplyAsync(output -> {
                                                finishEnrichment(enrichmentIndex, output);
                                                return null;
                                            },
                                            executor));
                }
                ++index;
            }
        }

        synchronized void finishEnrichment(int index, EntityFieldReadAccessor enrichmentOutput) {
            enrichmentsFinished.set(index);
            knownFields.addAll(enrichmentOutput.getSchema().getFields());
            enrichmentOutput.mergeInto(result);
            if (enrichmentsFinished.cardinality() == enrichments.getEnrichments().size()) {
                future.complete(outputFactory.build(result));
            }
            // TODO only apply recursion if other enrichments depend on this enrichment (maybe pass dependents as arguments?)
            progress();
        }

    }

}