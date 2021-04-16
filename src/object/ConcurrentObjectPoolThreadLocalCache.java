package eu.menzani.object;

import eu.menzani.concurrent.CleanedThreadLocal;

public class ConcurrentObjectPoolThreadLocalCache<T extends PoolObject> implements ObjectPool<T> {
    private final ObjectPool<T> parent;
    private final CleanedThreadLocal<ObjectPool<T>> buffers;

    public ConcurrentObjectPoolThreadLocalCache(ObjectFactory<ObjectPool<T>> bufferFactory, ObjectPool<T> parent) {
        this.parent = parent;
        buffers = new CleanedThreadLocal<>(bufferFactory);
    }

    @Override
    public T release() {
        ObjectPool<T> buffer = buffers.get();
        if (buffer.isEmpty()) {
            return parent.release();
        }
        return buffer.release();
    }

    @Override
    public void acquire(T object) {
        ObjectPool<T> buffer = buffers.get();
        if (buffer.isFull()) {
            parent.acquire(object);
        } else {
            buffer.acquire(object);
        }
    }

    @Override
    public boolean isFull() {
        return parent.isFull();
    }

    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public void gc() {
        parent.gc();
    }
}
