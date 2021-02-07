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
    private int index;

    public ResizableArray(Class<T> elementType) {
        this(10, elementType);
    }

    public ResizableArray(int initialLength, Class<T> elementType) {
        this.elementType = elementType;
        array = createArray(initialLength);
    }

    public void add(T element) {
        int index = this.index++;
        int length = array.length;
        if (index == length) {
            array = createArray(length * 2);
        }
        array[index] = element;
    }

    @SuppressWarnings("unchecked")
    private T[] createArray(int length) {
        return (T[]) Array.newInstance(elementType, length);
    }

    public T[] asFixedArray() {
        return Arrays.copyOf(array, index);
    }

    public @Optional T[] asFixedArrayOrNull() {
        if (index == 0) {
            return null;
        }
        return asFixedArray();
    }

    public ArrayView<T> view() {
        return new ArrayView<>(array);
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
