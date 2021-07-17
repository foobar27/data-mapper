package data.refinery.utils;

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


}
