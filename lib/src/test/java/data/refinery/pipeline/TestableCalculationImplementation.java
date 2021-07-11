package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;

public abstract class TestableCalculationImplementation implements CalculationImplementation {

    protected abstract EntityFieldReadAccessor calculate(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters);

    @Override
    public CompletableFuture<EntityFieldReadAccessor> apply(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters) {
        // TODO add functionality to block from the outside
        return CompletableFuture.supplyAsync(() -> calculate(input, parameters));
    }
}
