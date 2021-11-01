package com.github.foobar27.datamapper.utils;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public final class TestUtils {

    public static Throwable getExceptionNow(CompletableFuture<?> future) {
        if (!future.isDone()) {
            throw new IllegalStateException();
        }
        if (!future.isCompletedExceptionally()) {
            return null;
        }
        AtomicReference<Throwable> result = new AtomicReference<>();
        future.exceptionally(t -> {
            result.set(t);
            return null;
        });
        return result.get();
    }

    public static <T> T getAssertOnlyElement(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            throw new AssertionError("Empty, expected 1 element!");
        }
        T result = iterator.next();
        if (iterator.hasNext()) {
            throw new AssertionError("More than one element!");
        }
        return result;
    }

    public static <T> T getAssertOnlyElement(Iterable<T> iterable) {
        return getAssertOnlyElement(iterable.iterator());
    }

}
