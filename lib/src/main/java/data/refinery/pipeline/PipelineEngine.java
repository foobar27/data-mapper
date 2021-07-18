package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.CompletableFuture;

public interface PipelineEngine<OutputType extends EntityFieldReadAccessor, OutputBuilderType extends EntityFieldReadWriteAccessor> {

    CompletableFuture<OutputType> process(EntityFieldReadAccessor entity);

}
