package com.github.foobar27.datamapper.utils;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public final class FutureUtils {

    private FutureUtils() {
        // hide public constructor
    }

    public static <T, U> CompletableFuture<U> thenApplyAsyncWithCancellation(
            CompletableFuture<T> future,
            Function<? super T, ? extends U> fn,
            Executor executor) {
        CompletableFuture<U> output = future.thenApplyAsync(fn, executor);
        output.whenCompleteAsync((vv, t) -> {
            if (t instanceof CancellationException) {
                future.cancel(false);
            }
        });
        return output;
    }

}
