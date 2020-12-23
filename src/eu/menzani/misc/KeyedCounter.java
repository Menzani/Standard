package eu.menzani.misc;

public interface KeyedCounter<K> {
    int increment(K key);

    void reset(K key);

    void reset();
}
