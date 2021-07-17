package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.Field;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

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

    boolean areIndependent(EnrichmentList enrichments) {
        Set<Field> inputFields = enrichments.getAllInputFields();
        Set<Field> outputFields = enrichments.getAllOutputFields();
        return inputFields
                .stream()
                .noneMatch(outputFields::contains);
    }

}