package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;
import data.refinery.utils.AllOrNothingFutureContainer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

final class GenericPipelineEngine implements PipelineEngine {

    private final EnrichmentList enrichments;
    private final CalculationFactory calculationFactory;
    private final Supplier<EntityFieldReadWriteAccessor> outputFactory;
    private final Executor executor;

    GenericPipelineEngine(
            EnrichmentList enrichments,
            CalculationFactory calculationFactory,
            Supplier<EntityFieldReadWriteAccessor> outputFactory,
            Executor executor) {
        this.enrichments = checkNotNull(enrichments);
        this.calculationFactory = checkNotNull(calculationFactory);
        this.outputFactory = outputFactory;
        this.executor = executor;
    }

    public CompletableFuture<EntityFieldReadAccessor> process(EntityFieldReadAccessor inputEntity) {
        Process process = new Process(inputEntity, enrichments);
        return process.future;
    }

    private final class Process {
        private final CompletableFuture<EntityFieldReadAccessor> future = new CompletableFuture<>();
        private final EntityFieldReadWriteAccessor result = outputFactory.get();
        private final Set<Field> knownFields;
        private final Set<Enrichment> remainingEnrichments;
        private final AllOrNothingFutureContainer<EntityFieldReadAccessor> pendingFutures =
                new AllOrNothingFutureContainer<>(
                        future::completeExceptionally,
                        executor);

        Process(EntityFieldReadAccessor inputEntity, EnrichmentList enrichments) {
            this.knownFields = new HashSet<>(enrichments.getFixedSchema().getFields());
            remainingEnrichments = new HashSet<>(enrichments.getEnrichments());
            this.progress(null, inputEntity.filterFields(knownFields));
        }

        void start() {
            progress(null, null);
        }

        synchronized void progress(Enrichment finishedEnrichment, EntityFieldReadAccessor enrichmentOutput) {
            if (finishedEnrichment != null) {
                remainingEnrichments.remove(finishedEnrichment);
            }
            if (enrichmentOutput != null) {
                knownFields.addAll(enrichmentOutput.getSchema().getFields());
                enrichmentOutput.mergeInto(result);
            }
            if (remainingEnrichments.isEmpty()) {
                future.complete(result);
            } else {
                Map<Enrichment, CompletableFuture<EntityFieldReadAccessor>> enrichmentFutures = new HashMap<>();
                for (Enrichment enrichment : remainingEnrichments) {
                    if (knownFields.containsAll(enrichment.getMappedCalculation().getMapping().getLeftMapping().getMapping().keySet())) {
                        // All dependencies satisfied
                        CompletableFuture<EntityFieldReadAccessor> enrichmentFuture = pendingFutures.add(() ->
                                calculationFactory.apply(enrichment.getMappedCalculation().getCalculation())
                                        .wrap(enrichment.getMappedCalculation().getMapping()) // TODO shouldn't the wrapping be part of the Enrichment logic? Maybe a class EnrichmentImplementation?
                                        .apply(result, enrichment.getParameters()));// this must be BEFORE the recursion!
                        enrichmentFutures.put(enrichment, enrichmentFuture);
                    }
                }
                for (Map.Entry<Enrichment, CompletableFuture<EntityFieldReadAccessor>> entry : enrichmentFutures.entrySet()) {
                    entry.getValue()
                            // Recursion!
                            .thenAccept(calculationOutput -> progress(entry.getKey(), calculationOutput)); // TODO specify executor
                }
            }
        }

    }

}