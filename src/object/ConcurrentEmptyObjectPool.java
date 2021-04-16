package eu.menzani.object;

import eu.menzani.atomic.AtomicArray;
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
