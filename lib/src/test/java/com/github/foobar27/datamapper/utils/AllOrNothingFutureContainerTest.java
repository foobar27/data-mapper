package com.github.foobar27.datamapper.utils;

import com.google.common.util.concurrent.MoreExecutors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.foobar27.datamapper.utils.TestUtils.getExceptionNow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllOrNothingFutureContainerTest {

    private AtomicReference<Throwable> exception;
    private TestableFutures<String, String> futures;
    private AllOrNothingFutureContainer<String> container;

    @BeforeEach
    public void setup() {
        exception = new AtomicReference<>();
        futures = new TestableFutures<>(MoreExecutors.directExecutor());
        container = new AllOrNothingFutureContainer<>(exception::set, MoreExecutors.directExecutor());
    }

    private CompletableFuture<String> add(String s) {
        return container.add(() -> futures.put(s, () -> s));
    }

    @Test
    public void successUntilException() throws ExecutionException, InterruptedException {
        // Add f1
        CompletableFuture<String> f1 = add("f1");
        assertTrue(futures.hasPendingFutures());
        assertFalse(f1.isDone());
        futures.getAndRemoveUnblocker("f1").complete(null);
        assertEquals("f1", f1.get());

        // Add f2, f3
        CompletableFuture<String> f2 = add("f2");
        CompletableFuture<String> f3 = add("f3");
        assertTrue(futures.hasPendingFutures());
        assertFalse(f2.isDone());
        assertFalse(f3.isDone());
        futures.getAndRemoveUnblocker("f3").complete(null);
        futures.getAndRemoveUnblocker("f2").complete(null);
        assertEquals("f2", f2.get());
        assertEquals("f3", f3.get());

        // Add f4, f5, fail f4
        CompletableFuture<String> f4 = add("f4");
        CompletableFuture<String> f5 = add("f5");
        futures.getAndRemoveUnblocker("f4").completeExceptionally(new IOException());
        assertTrue(f4.isDone());
        assertTrue(f5.isDone());
        assertThat(getExceptionNow(f4), is(instanceOf(IOException.class)));
        assertThat(getExceptionNow(f5), is(instanceOf(CancellationException.class)));

        // Add f6 (should fail immediately, future should not even be created)
        CompletableFuture<String> f6 = add("f6");
        assertThrows(NoSuchElementException.class, () -> futures.getAndRemoveUnblocker("f4"));
        assertThat(getExceptionNow(f6), is(instanceOf(CancellationException.class)));
        assertThat(futures.getPendingUnblockers().keys(), is(empty()));

        assertThat(exception.get(), is(instanceOf(IOException.class)));
    }

}
