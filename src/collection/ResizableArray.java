package eu.menzani.collection;

import eu.menzani.lang.Optional;
import eu.menzani.struct.Arrays;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ResizableArray<T> implements Iterable<T> {
    private T[] array;
    private int length;

    public ResizableArray(Class<T> elementType) {
        this(10, elementType);
    }

    public ResizableArray(int initialLength, Class<T> elementType) {
        array = Arrays.allocateGeneric(elementType, initialLength);
    }

    public void add(T element) {
        int length = this.length++;
        if (length == array.length) {
            array = java.util.Arrays.copyOf(array, length * 2);
        }
        array[length] = element;
    }

    public T[] asFixedArray() {
        return java.util.Arrays.copyOf(array, length);
    }

    public @Optional T[] asFixedArrayOrNull() {
        if (length == 0) {
            return null;
        }
        return asFixedArray();
    }

    public ArrayView<T> view() {
        return new ArrayView<>(array, 0, length);
    }

    @Override
    public Iterator<T> iterator() {
        return view().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        view().forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return view().spliterator();
    }
}
