package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class PipelineEngineFactory {

    public PipelineEngine createPipelineEngine(CalculationFactory calculationFactory, Supplier<EntityFieldReadWriteAccessor> outputFactory, Executor executor) {
        return new GenericPipelineEngine(calculationFactory, outputFactory, executor);
    }

}