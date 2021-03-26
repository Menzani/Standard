package eu.menzani.object;

import eu.menzani.struct.Arrays;

import java.util.function.Supplier;

public class ConcurrentPrefilledObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private int index;

    public ConcurrentPrefilledObjectPool(int capacity, Supplier<? extends T> filler) {
        objects = Arrays.allocateGeneric(capacity);
        Arrays.fill(objects, filler);
        index = capacity;
    }

    @Override
    public T release() {
        T object;
        synchronized (this) {
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
