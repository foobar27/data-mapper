package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.utils.TestableFutures;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class TestableCalculationImplementation implements CalculationImplementation {

    private final TestableFutures<Map.Entry<EntityFieldReadAccessor, EntityFieldReadAccessor>, EntityFieldReadAccessor> futures;

    public TestableCalculationImplementation(Executor executor) {
        this.futures = new TestableFutures<>(executor);
    }

    public final TestableFutures<Map.Entry<EntityFieldReadAccessor, EntityFieldReadAccessor>, EntityFieldReadAccessor> getPendingFutures() {
        return futures;
    }

    protected abstract EntityFieldReadAccessor calculate(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters);

    @Override
    public CompletableFuture<EntityFieldReadAccessor> apply(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters) {
        return futures.put(
                new AbstractMap.SimpleEntry<>(input, parameters),
                () -> calculate(input, parameters)); // TODO make the executor configurable from the outside
    }
}
