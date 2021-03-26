package eu.menzani.collection;

import eu.menzani.lang.Optional;

import java.util.Arrays;

public class ResizableIntArray {
    private int[] array;
    private int index;

    public ResizableIntArray() {
        this(10);
    }

    public ResizableIntArray(int initialLength) {
        array = new int[initialLength];
    }

    public void add(int element) {
        int index = this.index++;
        int length = array.length;
        if (index == length) {
            array = new int[length * 2];
        }
        array[index] = element;
    }

    public int[] asFixedArray() {
        return Arrays.copyOf(array, index);
    }

    public @Optional int[] asFixedArrayOrNull() {
        if (index == 0) {
            return null;
        }
        return asFixedArray();
    }

    public IntArrayView view() {
        return new IntArrayView(asFixedArray());
    }
}
