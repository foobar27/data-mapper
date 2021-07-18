package data.refinery.pipeline;

import data.refinery.conversion.EntityFactory;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.CompletableFuture;

final class DummyPipelineEngine <OutputType extends EntityFieldReadAccessor, OutputBuilderType extends EntityFieldReadWriteAccessor> implements PipelineEngine<OutputType, OutputBuilderType> {

    private final EntityFactory<OutputType, OutputBuilderType> outputFactory;

    DummyPipelineEngine(EntityFactory<OutputType, OutputBuilderType> outputFactory) {
        this.outputFactory = outputFactory;
    }


    @Override
    public CompletableFuture<OutputType> process(EntityFieldReadAccessor inputEntity) {
        return CompletableFuture.completedFuture(outputFactory.build(outputFactory.newBuilder(inputEntity)));
    }
}
