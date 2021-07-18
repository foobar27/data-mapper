package data.refinery.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class TestableFutures<K, V> {

    private final Storage<K> futures = new Storage<K>();

    private volatile boolean autoApply = false;

    public void enableAutoApply() {
        this.autoApply = true;
        getPendingUnblockers().values().forEach(x -> x.complete(null));
    }

    public CompletableFuture<V> put(K key, Supplier<V> valueSupplier) {
        // TODO operation mode: default unblock
        CompletableFuture<V> resultFuture = new CompletableFuture<>();
        CompletableFuture<Void> unblockingFuture = new CompletableFuture<>();
        futures.put(key, unblockingFuture);
        unblockingFuture.thenRun(() -> resultFuture.complete(valueSupplier.get())); // TODO executor
        unblockingFuture.exceptionally(t -> {
            // In case the unblockingFuture has been cancelled, we receive a CancellationException here.
            resultFuture.completeExceptionally(t);
            return null;
        });
        resultFuture.exceptionally(t -> {
            unblockingFuture.completeExceptionally(t);
            return null;
        });
        // TODO remove from "futures" (in successful case, and in exceptional case)
        if (autoApply) {
            unblockingFuture.complete(null);
        }
        return resultFuture;
    }

    public CompletableFuture<Void> getAndRemoveUnblocker(K key) {
        return futures.getAndRemove(key);
    }

    public boolean hasPendingFutures() {
        return futures.hasPendingFutures();
    }

    public Multimap<K, CompletableFuture<Void>> getPendingUnblockers() {
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
