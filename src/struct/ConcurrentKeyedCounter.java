package eu.menzani.struct;

import eu.menzani.atomic.AtomicInt;
import eu.menzani.lang.Lang;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentKeyedCounter<K> implements KeyedCounter<K> {
    private final ConcurrentMap<K, Count> counts = new ConcurrentHashMap<>();

    @Override
    public int increment(K key) {
        return counts.computeIfAbsent(key, k -> new Count()).getAndIncrement();
    }

    @Override
    public void reset(K key) {
        counts.remove(key);
    }

    @Override
    public void reset() {
        counts.clear();
    }

    private static class Count {
        private static final long VALUE = Lang.objectFieldOffset(Count.class, "value");

        private int value;

        Count() {
            AtomicInt.setOpaque(this, VALUE, 1);
        }

        int getAndIncrement() {
            return AtomicInt.getAndIncrementVolatile(this, VALUE);
        }
    }
}
