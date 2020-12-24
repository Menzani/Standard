package eu.menzani.struct;

public interface KeyedCounter<K> {
    int increment(K key);

    void reset(K key);

    void reset();
}
