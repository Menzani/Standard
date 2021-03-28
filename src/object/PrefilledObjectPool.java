package eu.menzani.object;

import eu.menzani.struct.Arrays;

public class PrefilledObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private int index;

    public PrefilledObjectPool(int capacity, ObjectFactory<T> filler) {
        objects = Arrays.allocateGeneric(PoolObject.class, capacity);
        Arrays.fill(objects, filler);
        index = capacity;
    }

    @Override
    public T release() {
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
