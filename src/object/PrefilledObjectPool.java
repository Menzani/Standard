package eu.menzani.object;

import eu.menzani.struct.Arrays;

import java.util.function.Supplier;

public class PrefilledObjectPool<T extends PoolObject> implements ObjectPool<T> {
    private final T[] objects;
    private int index;

    public PrefilledObjectPool(int capacity, Supplier<? extends T> filler) {
        objects = Arrays.allocateGeneric(capacity);
        Arrays.fill(objects, filler);
        index = capacity;
    }

    @Override
    public T release() {
        T object = objects[--index];
        object.reconstruct();
        return object;
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
