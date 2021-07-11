package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class PipelineEngine {

    private final CalculationFactory calculationFactory;
    private final Supplier<EntityFieldReadWriteAccessor> outputFactory;
    private final Executor executor;

    public PipelineEngine(CalculationFactory calculationFactory, Supplier<EntityFieldReadWriteAccessor> outputFactory, Executor executor) {
        this.calculationFactory = checkNotNull(calculationFactory);
        this.outputFactory = outputFactory;
        this.executor = executor;
    }

    public CompletableFuture<EntityFieldReadWriteAccessor> process(EntityWithEnrichments input) {
        Process process = new Process(input);
        return process.future;
    }

    private final class Process {
        private final CompletableFuture<EntityFieldReadWriteAccessor> future = new CompletableFuture<>();
        private final EntityFieldReadWriteAccessor result = outputFactory.get();
        private final Set<Field> knownFields;
        private final Set<Enrichment> remainingEnrichments;
        private final Map<Enrichment, CompletableFuture<EntityFieldReadAccessor>> pendingFutures = new HashMap<>();

        Process(EntityWithEnrichments input) {
            this.knownFields = new HashSet<>(input.getEntity().getSchema().getFields());
            remainingEnrichments = new HashSet<>(input.getEnrichments());
            for (Enrichment enrichment : input.getEnrichments()) {
                enrichment.getMapping().getRightMapping().getOutputSchema().getFields().forEach(knownFields::remove);
            }
            this.progress(null, input.getEntity().filterFields(knownFields));
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
                for (Enrichment enrichment : remainingEnrichments) { // TODO just had a ConcurrentModificationException here!
                    if (knownFields.containsAll(enrichment.getMapping().getLeftMapping().getMapping().keySet())) {
                        // All dependencies satisfied
                        CompletableFuture<EntityFieldReadAccessor> enrichmentFuture = calculationFactory.apply(enrichment.getCalculation())
                                .wrap(enrichment.getMapping()) // TODO shouldn't the wrapping be part of the Enrichment logic? Maybe a class EnrichmentImplementation?
                                .apply(result, enrichment.getParameters());
                        pendingFutures.put(enrichment, enrichmentFuture); // this must be BEFORE the recursion!
                        enrichmentFuture
                                // Recursion!
                                .thenAccept(calculationOutput -> progress(enrichment, calculationOutput)) // TODO specify executor
                                .exceptionally(this::handleThrowable);
                    }
                }
            }
        }

    }

}