package eu.menzani.object;

import eu.menzani.struct.Arrays;

import java.util.function.Supplier;

public class ConcurrentEmptyObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private final Supplier<? extends T> factory;
    private int index;

    public ConcurrentEmptyObjectPool(int capacity, Supplier<? extends T> factory) {
        objects = Arrays.allocateGeneric(capacity);
        this.factory = factory;
    }

    @Override
    public T release() {
        T object;
        synchronized (this) {
            if (index == 0) {
                return factory.get();
            }
            object = objects[--index];
        }
        object.reconstruct();
        return object;
    }

    @Override
    public synchronized void acquire(T object) {
        objects[index++] = object;
    }

    @Override
    public synchronized void gc() {
        for (int i = 0; i < index; i++) {
            objects[i].gc();
        }
    }
}
