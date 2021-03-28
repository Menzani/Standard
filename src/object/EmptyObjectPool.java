package eu.menzani.object;

import eu.menzani.struct.Arrays;

public class EmptyObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private final ObjectFactory<T> factory;
    private int index;

    public EmptyObjectPool(int capacity, ObjectFactory<T> factory) {
        objects = Arrays.allocateGeneric(capacity);
        this.factory = factory;
    }

    @Override
    public T release() {
        if (index == 0) {
            return factory.newInstance();
        }
        return objects[--index];
    }

    @Override
    public void acquire(T object) {
        objects[index++] = object;
    }

    @Override
    public void gc() {
        for (int i = 0; i < index; i++) {
            objects[i].gc();
        }
    }
}
