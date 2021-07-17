package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class PipelineEngineFactory {

    public PipelineEngine createPipelineEngine(List<Enrichment> enrichments, CalculationFactory calculationFactory, Supplier<EntityFieldReadWriteAccessor> outputFactory, Executor executor) {
        return new GenericPipelineEngine(enrichments, calculationFactory, outputFactory, executor);
    }

}