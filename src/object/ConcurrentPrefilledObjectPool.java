package eu.menzani.object;

import eu.menzani.struct.Arrays;

public class ConcurrentPrefilledObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private int index;

    public ConcurrentPrefilledObjectPool(int capacity, ObjectFactory<T> filler) {
        objects = Arrays.allocateGeneric(PoolObject.class, capacity);
        Arrays.fill(objects, filler);
        index = capacity;
    }

    @Override
    public synchronized T release() {
        return objects[--index];
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
