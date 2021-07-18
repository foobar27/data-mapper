package data.refinery.utils;

import com.google.common.util.concurrent.MoreExecutors;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static data.refinery.utils.TestUtils.getExceptionNow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class TestableFuturesTest {

    @Test
    public void unblockShouldUnblockFuture() throws ExecutionException, InterruptedException {
        TestableFutures<String, String> futures = new TestableFutures<>(MoreExecutors.directExecutor());
        CompletableFuture<String> foo = futures.put("foo", () -> "FOO");
        CompletableFuture<String> bar = futures.put("bar", () -> "BAR");
        assertFalse(foo.isDone());
        assertTrue(futures.hasPendingFutures());

        futures.getAndRemoveUnblocker("foo").complete(null);
        assertTrue(foo.isDone());
        assertFalse(bar.isDone());
        assertTrue(futures.hasPendingFutures());
        assertEquals("FOO", foo.get());

        futures.getAndRemoveUnblocker("bar").complete(null);
        assertTrue(bar.isDone());
        assertFalse(futures.hasPendingFutures());
        assertEquals("BAR", bar.get());

        CompletableFuture<String> baz = futures.put("baz", () -> "BAZ");
        assertFalse(baz.isDone());
        assertTrue(futures.hasPendingFutures());
        futures.getAndRemoveUnblocker("baz").complete(null);
        assertTrue(baz.isDone());
        assertFalse(futures.hasPendingFutures());
        assertEquals("BAZ", baz.get());
    }

    @Test
    public void exceptionShouldPropagate() {
        TestableFutures<String, String> futures = new TestableFutures<>(MoreExecutors.directExecutor());
        CompletableFuture<String> foo = futures.put("foo", () -> "FOO");
        assertFalse(foo.isDone());
        assertFalse(!futures.hasPendingFutures());

        futures.getAndRemoveUnblocker("foo").completeExceptionally(new IOException());
        assertTrue(foo.isCompletedExceptionally());
        assertThat(getExceptionNow(foo), is(instanceOf(IOException.class)));
    }

    @Test
    public void cancellationShouldPropagate() {
        TestableFutures<String, String> futures = new TestableFutures<>(MoreExecutors.directExecutor());
        CompletableFuture<String> foo = futures.put("foo", () -> "FOO");
        assertFalse(foo.isDone());
        assertTrue(futures.hasPendingFutures());

        futures.getAndRemoveUnblocker("foo").cancel(false);
        assertTrue(foo.isCompletedExceptionally());
        assertTrue(foo.isCancelled());
        assertThat(getExceptionNow(foo), is(instanceOf(CancellationException.class)));
    }

    @Test
    public void multipleValuesCanBeUnblocked() throws ExecutionException, InterruptedException {
        TestableFutures<String, String> futures = new TestableFutures<>(MoreExecutors.directExecutor());
        CompletableFuture<String> foo1 = futures.put("foo", () -> "FOO1");
        CompletableFuture<String> foo2 = futures.put("foo", () -> "FOO2");
        assertFalse(foo1.isDone());
        assertFalse(foo2.isDone());
        assertTrue(futures.hasPendingFutures());

        futures.getAndRemoveUnblocker("foo").complete(null);
        assertTrue(futures.hasPendingFutures());
        // One of the two futures has been blocked, let's determine which one.
        assertTrue(foo1.isDone() != foo2.isDone());
        if (foo1.isDone()) {
            assertFalse(foo2.isDone());
            assertEquals("FOO1", foo1.get());

            // Unblock the second future.
            futures.getAndRemoveUnblocker("foo").complete(null);
            assertTrue(foo2.isDone());
            assertEquals("FOO2", foo2.get());
        } else {
            // foo2.isDone()
            assertFalse(foo1.isDone());
            assertEquals("FOO2", foo2.get());

            // Unblock the second future.
            futures.getAndRemoveUnblocker("foo").complete(null);
            assertTrue(foo1.isDone());
            assertEquals("FOO2", foo2.get());
        }
        assertFalse(futures.hasPendingFutures());
    }

}
