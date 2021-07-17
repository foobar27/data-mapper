package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;

final class DummyPipelineEngine implements PipelineEngine {

    @Override
    public CompletableFuture<EntityFieldReadAccessor> process(EntityFieldReadAccessor inputEntity) {
        return CompletableFuture.completedFuture(inputEntity);
    }
}
