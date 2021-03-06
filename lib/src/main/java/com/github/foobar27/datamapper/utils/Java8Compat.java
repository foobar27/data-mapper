package com.github.foobar27.datamapper.utils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Java8Compat {

    private Java8Compat() {
        // inhibit public constructor
    }

    // workaround for CompletableFuture.failedFuture()
    public static <U> CompletableFuture<U> failedFuture(Throwable ex) {
        CompletableFuture<U> result = new CompletableFuture<>();
        result.completeExceptionally(ex);
        return result;
    }

    // workaround for Optional.stream()
    public static <U> Stream<U> optionalToStream(Optional<U> input) {
        return input.map(Stream::of).orElseGet(Stream::empty);
    }

}
