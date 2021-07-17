package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

class GenericPipelineEngine implements PipelineEngine {

    private final List<Enrichment> enrichments;
    private final CalculationFactory calculationFactory;
    private final Supplier<EntityFieldReadWriteAccessor> outputFactory;
    private final Executor executor;

    GenericPipelineEngine(
            List<Enrichment> enrichments,
            CalculationFactory calculationFactory,
            Supplier<EntityFieldReadWriteAccessor> outputFactory,
            Executor executor) {
        this.enrichments = ImmutableList.copyOf(enrichments);
        this.calculationFactory = checkNotNull(calculationFactory);
        this.outputFactory = outputFactory;
        this.executor = executor;
    }

    public CompletableFuture<EntityFieldReadWriteAccessor> process(EntityFieldReadAccessor input) {
        Process process = new Process(input, enrichments);
        return process.future;
    }

    private final class Process {
        private final CompletableFuture<EntityFieldReadWriteAccessor> future = new CompletableFuture<>();
        private final EntityFieldReadWriteAccessor result = outputFactory.get();
        private final Set<Field> knownFields;
        private final Set<Enrichment> remainingEnrichments;
        private final Map<Enrichment, CompletableFuture<EntityFieldReadAccessor>> pendingFutures = new HashMap<>();

        Process(EntityFieldReadAccessor inputEntity, List<Enrichment> enrichments) {
            this.knownFields = new HashSet<>(inputEntity.getSchema().getFields());
            remainingEnrichments = new HashSet<>(enrichments);
            for (Enrichment enrichment : enrichments) {
                enrichment.getMappedCalculation().getMapping().getRightMapping().getOutputSchema().getFields().forEach(knownFields::remove);
            }
            this.progress(null, inputEntity.filterFields(knownFields));
        }

        void start() {
            progress(null, null);
        }

        synchronized Void handleThrowable(Throwable t) {
            pendingFutures.values().forEach(x -> x.cancel(false));
            future.completeExceptionally(t);
            return null;
        }

        synchronized void progress(Enrichment finishedEnrichment, EntityFieldReadAccessor enrichmentOutput) {
            if (finishedEnrichment != null) {
                remainingEnrichments.remove(finishedEnrichment);
                pendingFutures.remove(finishedEnrichment);
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
                        CompletableFuture<EntityFieldReadAccessor> enrichmentFuture = calculationFactory.apply(enrichment.getMappedCalculation().getCalculation())
                                .wrap(enrichment.getMappedCalculation().getMapping()) // TODO shouldn't the wrapping be part of the Enrichment logic? Maybe a class EnrichmentImplementation?
                                .apply(result, enrichment.getParameters());
                        pendingFutures.put(enrichment, enrichmentFuture); // this must be BEFORE the recursion!
                        enrichmentFutures.put(enrichment, enrichmentFuture);
                    }
                }
                for (Map.Entry<Enrichment, CompletableFuture<EntityFieldReadAccessor>> entry : enrichmentFutures.entrySet()) {
                    entry.getValue()
                            // Recursion!
                            .thenAccept(calculationOutput -> progress(entry.getKey(), calculationOutput)) // TODO specify executor
                            .exceptionally(this::handleThrowable);
                }
            }
        }

    }

}