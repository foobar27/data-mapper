package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.conversion.EntityFactory;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;
import com.github.foobar27.datamapper.schema.Field;
import com.github.foobar27.datamapper.utils.AllOrNothingFutureContainer;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import static com.github.foobar27.datamapper.utils.FutureUtils.thenApplyAsyncWithCancellation;
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
            future.whenCompleteAsync((result, ex) -> {
                if (ex instanceof CancellationException) {
                    pendingFutures.cancel(false);
                }
            }, executor);
            progress();
        }

        synchronized void progress() {
            int index = 0;
            for (Enrichment enrichment : enrichments.getEnrichments()) {
                final int enrichmentIndex = index++;
                if (enrichmentsTriggered.get(enrichmentIndex)) {
                    continue;
                }
                if (knownFields.containsAll(enrichment.getMappedCalculation().getMapping().getLeftMapping().getMapping().keySet())) {
                    enrichmentsTriggered.set(enrichmentIndex);
                    // All dependencies satisfied
                    pendingFutures.add(() ->
                            thenApplyAsyncWithCancellation(
                                    applyEnrichment(enrichment),
                                    // Recursion!
                                    (output -> {
                                        finishEnrichment(enrichmentIndex, output);
                                        return null;
                                    }),
                                    executor));
                }
            }
        }

        private synchronized CompletableFuture<EntityFieldReadAccessor> applyEnrichment(Enrichment enrichment) {
            return enrichment.apply(result, calculationFactory);
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