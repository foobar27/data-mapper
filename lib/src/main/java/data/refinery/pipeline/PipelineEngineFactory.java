package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class PipelineEngineFactory {

    public PipelineEngine createPipelineEngine(List<Enrichment> enrichments, CalculationFactory calculationFactory, Supplier<EntityFieldReadWriteAccessor> outputFactory, Executor executor) {
        if (enrichments.isEmpty()) {
            return new DummyPipelineEngine();
        }
//        if (areIndependent(enrichments)) {
//            return new IndependentEnrichmentPipeline(enrichments, calculationFactory, outputFactory, executor);
//        }
        return new GenericPipelineEngine(enrichments, calculationFactory, outputFactory, executor);
    }

    boolean areIndependent(List<Enrichment> enrichments) {
        Set<Field> outputFields = enrichments.stream()
                .flatMap(enrichment ->
                        enrichment.getMappedCalculation()
                                .getMapping()
                                .getLeftMapping()
                                .getMapping()
                                .keySet()
                                .stream())
                .collect(Collectors.toSet());
        // Is any output field included in the input fields?
        return enrichments.stream()
                .flatMap(enrichment ->
                        enrichment.getMappedCalculation()
                                .getMapping()
                                .getLeftMapping()
                                .getMapping()
                                .keySet()
                                .stream())
                .noneMatch(outputFields::contains);
    }

}