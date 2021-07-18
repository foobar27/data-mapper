package data.refinery.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A dynamic list of CompletableFutures.
 * If one of them fails, every Future is cancelled (including Futures which will be added in the future).
 * Addtionally, a failure will also trigger a user-provided exceptionHandler.
 *
 * Keeps the full history of futures in memory, so you probably don't want to use this for a big amount of futures.
 */
public class AllOrNothingFutureContainer<V> {

    private final Consumer<Throwable> exceptionHandler;
    private final Executor exceptionHandlerExecutor;
    private final List<CompletableFuture<V>> futures = new ArrayList<>();
    private boolean failed = false;

    public AllOrNothingFutureContainer(Consumer<Throwable> exceptionHandler, Executor exceptionHandlerExecutor) {
        this.exceptionHandler = checkNotNull(exceptionHandler);
        this.exceptionHandlerExecutor = exceptionHandlerExecutor;
    }

    private synchronized void handleException(Throwable t) {
        futures.forEach(f -> f.cancel(false));
        exceptionHandler.accept(t);
        failed = true;
    }

    /**
     * Adds a Future to the container.
     * In case the AllOrNothingFutureList is in a failed state, we do not instantiate the future,
     * but return a failed future immediately.
     */
    public synchronized CompletableFuture<V> add(Supplier<CompletableFuture<V>> futureSupplier) {
        if (failed) {
            return CompletableFuture.failedFuture(new CancellationException());
        } else {
            CompletableFuture<V> future = futureSupplier.get();
            this.futures.add(future);
            future.exceptionallyAsync(
                    t -> {
                        handleException(t);
                        return null;
                    },
                    exceptionHandlerExecutor);
            return future;
        }

    }

}
