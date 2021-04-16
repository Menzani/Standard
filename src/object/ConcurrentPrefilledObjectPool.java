package eu.menzani.object;

import eu.menzani.atomic.AtomicArray;
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
        return AtomicArray.getPlain(objects, --index);
    }

    @Override
    public synchronized void acquire(T object) {
        AtomicArray.setPlain(objects, index++, object);
    }

    @Override
    public synchronized boolean isFull() {
        return index == objects.length;
    }

    @Override
    public synchronized boolean isEmpty() {
        return index == 0;
    }

    @Override
    public synchronized void gc() {
        for (int i = 0; i < index; i++) {
            AtomicArray.getPlain(objects, i).gc();
        }
    }
}
