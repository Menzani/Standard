package eu.menzani.collection;

import eu.menzani.lang.Check;
import eu.menzani.lang.Optional;
import eu.menzani.object.GarbageCollectionAware;
import eu.menzani.struct.Arrays;

import java.util.Collection;

public class Buffer<T> implements GarbageCollectionAware {
    private T[] elements;
    private int addIndex;

    public static <T> Buffer<T> createOrClearAndAddAll(@Optional Buffer<T> buffer, Collection<T> collection) {
        return createOrClearAndAddAll(buffer, collection.size(), collection);
    }

    public static <T> Buffer<T> createOrClearAndAddAll(@Optional Buffer<T> buffer, int amount, Iterable<T> iterable) {
        if (buffer == null) {
            return new Buffer<>(amount, iterable);
        }
        buffer.clearAndAddAll(amount, iterable);
        return buffer;
    }

    public Buffer() {
        this(10);
    }

    public Buffer(int initialCapacity) {
        elements = Arrays.allocateGeneric(initialCapacity);
    }

    public Buffer(Collection<T> collection) {
        this(collection.size(), collection);
    }

    public Buffer(int amount, Iterable<T> iterable) {
        elements = Arrays.allocateGeneric(amount + 10);
        int i = 0;
        for (T element : iterable) {
            elements[i++] = element;
        }
        Check.equal(i, amount);
        addIndex = amount;
    }

    public void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            resizeElements(capacity);
        }
    }

    public void add(T element) {
        int index = addIndex++;
        if (index == elements.length) {
            resizeElements(index * 2);
        }
        elements[index] = element;
    }

    private void resizeElements(int newCapacity) {
        elements = java.util.Arrays.copyOf(elements, newCapacity);
    }

    public boolean remove(T element) {
        for (int i = 0; i < addIndex; i++) {
            if (element.equals(elements[i])) {
                elements[i] = null;
                return true;
            }
        }
        return false;
    }

    public void addAll(Collection<T> collection) {
        addAll(collection.size(), collection);
    }

    public void addAll(int amount, Iterable<T> iterable) {
        int endIndex = addIndex + amount;
        ensureCapacity(endIndex + 10);
        int i = addIndex;
        for (T element : iterable) {
            elements[i++] = element;
        }
        Check.equal(i, endIndex);
        addIndex = endIndex;
    }

    public void clearAndAddAll(Collection<T> collection) {
        clearAndAddAll(collection.size(), collection);
    }

    public void clearAndAddAll(int amount, Iterable<T> iterable) {
        ensureCapacity(amount + 10);
        int i = 0;
        for (T element : iterable) {
            elements[i++] = element;
        }
        Check.equal(i, amount);
        for (; i < addIndex; i++) {
            elements[i] = null;
        }
        addIndex = amount;
    }

    public boolean isEmpty() {
        for (int i = 0; i < addIndex; i++) {
            if (elements[i] != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void gc() {
        resizeElements(addIndex + 10);
    }
}
