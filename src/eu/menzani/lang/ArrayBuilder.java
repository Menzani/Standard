package eu.menzani.lang;

import eu.menzani.lang.mutable.BooleanRef;

public class ArrayBuilder<E> {
    private E[] array;
    private int index;

    public ArrayBuilder() {
        this(10);
    }

    public ArrayBuilder(int capacity) {
        extendArray(capacity, null);
    }

    public ArrayBuilder(E[] array) {
        index = array.length;
        extendArray(index + 10, array);
    }

    public void add(E element) {
        if (index == array.length) {
            extendArray(array.length * 2, array);
        }
        array[index++] = element;
    }

    @SuppressWarnings("unchecked")
    private void extendArray(int length, @Optional E[] currentArray) {
        array = (E[]) new Object[length];
        if (currentArray != null) {
            System.arraycopy(currentArray, 0, this.array, 0, currentArray.length);
        }
    }

    public void map(Mapper<E> mapper) {
        BooleanRef shouldRemove = new BooleanRef();
        for (int i = 0; i < index; i++) {
            shouldRemove.value = false;
            E newElement = mapper.map(array[i], shouldRemove);
            if (shouldRemove.value) {
                if (i != index - 1) {
                    System.arraycopy(array, i + 1, array, i, index - (i + 1));
                }
                index--;
            } else {
                array[i] = newElement;
            }
        }
    }

    public E[] build() {
        return array;
    }

    public interface Mapper<E> {
        E map(E element, BooleanRef shouldRemove);
    }
}
