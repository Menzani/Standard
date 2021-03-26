package eu.menzani.object;

public interface ObjectPool<T extends PoolObject> extends GarbageCollected {
    T release();

    void acquire(T object);
}
