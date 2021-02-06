package eu.menzani.struct;

import java.util.HashMap;
import java.util.Map;

public class SimpleKeyedCounter<K> implements KeyedCounter<K> {
    private final Map<K, Count> counts = new HashMap<>();

    @Override
    public int increment(K key) {
        Count count = counts.get(key);
        if (count == null) {
            count = new Count();
            counts.put(key, count);
        }
        return count.value++;
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
        int value = 1;
    }
}
