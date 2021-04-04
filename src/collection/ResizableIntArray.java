package eu.menzani.collection;

import eu.menzani.lang.Optional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ResizableIntArray implements Iterable<Integer> {
    private int[] array;
    private int length;

    public ResizableIntArray() {
        this(10);
    }

    public ResizableIntArray(int initialLength) {
        array = new int[initialLength];
    }

    public void add(int element) {
        int length = this.length++;
        if (length == array.length) {
            array = Arrays.copyOf(array, length * 2);
        }
        array[length] = element;
    }

    public int[] asFixedArray() {
        return Arrays.copyOf(array, length);
    }

    public @Optional int[] asFixedArrayOrNull() {
        if (length == 0) {
            return null;
        }
        return asFixedArray();
    }

    public IntArrayView view() {
        return new IntArrayView(array, 0, length);
    }

    @Override
    public Iterator<Integer> iterator() {
        return view().iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        view().forEach(action);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return view().spliterator();
    }
}
