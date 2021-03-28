package eu.menzani.object;

public interface PoolObject extends GarbageCollectionAware {
    void deallocate();
}
