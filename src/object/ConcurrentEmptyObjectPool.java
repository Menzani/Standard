package eu.menzani.object;

import eu.menzani.struct.Arrays;

public class ConcurrentEmptyObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private final ObjectFactory<T> factory;
    private int index;

    public ConcurrentEmptyObjectPool(int capacity, ObjectFactory<T> factory) {
        objects = Arrays.allocateGeneric(PoolObject.class, capacity);
        this.factory = factory;
    }

    @Override
    public synchronized T release() {
        if (index == 0) {
            return factory.newInstance();
        }
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
