package eu.menzani.collection;

import eu.menzani.lang.Optional;

import java.util.Arrays;

public class ResizableIntArray {
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
        int arrayLength = array.length;
        if (length == arrayLength) {
            array = new int[arrayLength * 2];
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
        return new IntArrayView(asFixedArray());
    }
}
