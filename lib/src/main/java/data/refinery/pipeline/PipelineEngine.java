package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;

public interface PipelineEngine {

    CompletableFuture<EntityFieldReadAccessor> process(EntityFieldReadAccessor entity);

}
