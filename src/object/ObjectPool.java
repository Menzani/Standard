package eu.menzani.object;

public interface ObjectPool<T extends PoolObject> extends GarbageCollectionAware {
    T release();

    void acquire(T object);

    boolean isFull();

    boolean isEmpty();
}
