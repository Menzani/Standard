package eu.menzani.collection;

import eu.menzani.lang.Assume;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.Consumer;

public class ArrayView<T> extends AbstractList<T> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0L;

    private final T[] array;
    private final int fromIndex;

    public ArrayView(T[] array) {
        this(array, 0);
    }

    public ArrayView(T[] array, int fromIndex) {
        Assume.notNegative(fromIndex);
        Assume.lesserThan(fromIndex, array.length);
        this.array = array;
        this.fromIndex = fromIndex;
    }

    @Override
    public int size() {
        return array.length - fromIndex;
    }

    @Override
    public T get(int index) {
        Assume.notNegative(index);
        return array[fromIndex + index];
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = fromIndex; i < array.length; i++) {
            action.accept(array[i]);
        }
    }

    private class Iterator implements java.util.Iterator<T> {
        private int cursor = fromIndex;

        @Override
        public boolean hasNext() {
            return cursor < array.length;
        }

        @Override
        public T next() {
            if (cursor == array.length) {
                throw new NoSuchElementException();
            }
            return array[cursor++];
        }
    }
}