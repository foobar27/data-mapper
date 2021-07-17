package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.CompletableFuture;

public interface PipelineEngine {

    CompletableFuture<EntityFieldReadWriteAccessor> process(EntityWithEnrichments input);

}
