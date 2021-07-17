package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.utils.TestableFutures;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class TestableCalculationImplementation implements CalculationImplementation {

    private final TestableFutures<Map.Entry<EntityFieldReadAccessor, EntityFieldReadAccessor>, EntityFieldReadAccessor> futures =
            new TestableFutures<>();

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
