package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.CompletableFuture;

public interface PipelineEngine<OutputType extends EntityFieldReadAccessor, OutputBuilderType extends EntityFieldReadWriteAccessor> {

    CompletableFuture<OutputType> process(EntityFieldReadAccessor entity);

}
