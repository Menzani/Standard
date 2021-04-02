package eu.menzani.collection;

import eu.menzani.lang.Optional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ResizableArray<T> implements Iterable<T> {
    private final Class<T> elementType;
    private T[] array;
    private int length;

    public ResizableArray(Class<T> elementType) {
        this(10, elementType);
    }

    public ResizableArray(int initialLength, Class<T> elementType) {
        this.elementType = elementType;
        array = createArray(initialLength);
    }

    public void add(T element) {
        int length = this.length++;
        int arrayLength = array.length;
        if (length == arrayLength) {
            array = createArray(arrayLength * 2);
        }
        array[length] = element;
    }

    @SuppressWarnings("unchecked")
    private T[] createArray(int length) {
        return (T[]) Array.newInstance(elementType, length);
    }

    public T[] asFixedArray() {
        return Arrays.copyOf(array, length);
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
