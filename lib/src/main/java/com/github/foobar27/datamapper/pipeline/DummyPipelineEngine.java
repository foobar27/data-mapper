package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.conversion.EntityFactory;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;

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
