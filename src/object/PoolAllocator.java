package eu.menzani.object;

public class PoolAllocator<T extends PoolObject> implements Allocator<T> {
    private final ObjectPool<T> pool;

    public PoolAllocator(ObjectPool<T> pool) {
        this.pool = pool;
    }

    @Override
    public T allocate() {
        return pool.release();
    }

    @Override
    public void deallocate(T object) {
        pool.acquire(object);
    }
}
