package data.refinery.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public final class TestableFutures<K, V> {

    private final Storage<K> futures = new Storage<K>();

    private final Executor executor;
    private volatile boolean autoApply = false;

    public TestableFutures(Executor executor) {
        this.executor = executor;
    }

    public synchronized void enableAutoApply() {
        this.autoApply = true;
        getPendingUnblockers().values().forEach(x -> x.complete(null));
    }

    public synchronized CompletableFuture<V> put(K key, Supplier<V> valueSupplier) {
        // TODO operation mode: default unblock
        CompletableFuture<V> resultFuture = new CompletableFuture<>();
        CompletableFuture<Void> unblockingFuture = new CompletableFuture<>();
        futures.put(key, unblockingFuture);
        unblockingFuture.thenRunAsync(() -> resultFuture.complete(valueSupplier.get()), executor);
        unblockingFuture.exceptionally/*Async*/(t -> {
            // In case the unblockingFuture has been cancelled, we receive a CancellationException here.
            resultFuture.completeExceptionally(t);
            return null;
        }/*, executor*/);
        resultFuture.exceptionally/*Async*/(t -> {
            unblockingFuture.completeExceptionally(t);
            return null;
        }/*, executor*/);
        unblockingFuture.whenCompleteAsync(
                (v, t) -> futures.remove(key, unblockingFuture),
                executor);
        // TODO remove from "futures" (in successful case, and in exceptional case)
        if (autoApply) {
            unblockingFuture.complete(null);
        }
        return resultFuture;
    }

    public synchronized CompletableFuture<Void> getAndRemoveUnblocker(K key) {
        return futures.getAndRemove(key);
    }

    public synchronized boolean hasPendingFutures() {
        return futures.hasPendingFutures();
    }

    public synchronized Multimap<K, CompletableFuture<Void>> getPendingUnblockers() {
        return Multimaps.filterEntries(
                futures.snapshot(),
                entry -> !entry.getValue().isDone());
    }

    private static final class Storage<K> {
        private final ArrayListMultimap<K, CompletableFuture<Void>> futures = ArrayListMultimap.create();

        synchronized CompletableFuture<Void> getAndRemove(K key) {
            List<CompletableFuture<Void>> candidates = futures.get(key);
            if (candidates.isEmpty()) {
                throw new NoSuchElementException();
            }
            CompletableFuture<Void> result = candidates.get(0);
            candidates.remove(0);
            return result;
        }

        synchronized void put(K key, CompletableFuture<Void> future) {
            futures.put(key, future);
        }

        synchronized void remove(K key, CompletableFuture<Void> future) {
            futures.remove(key, future);
        }

        synchronized Multimap<K, CompletableFuture<Void>> snapshot() {
            return ImmutableMultimap.copyOf(futures);
        }

        synchronized boolean hasPendingFutures() {
            return futures.entries()
                    .stream()
                    .anyMatch(entry -> !entry.getValue().isDone());
        }

    }

}
