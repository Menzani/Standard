package eu.menzani.collection;

import eu.menzani.lang.Assume;
import eu.menzani.struct.Arrays;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayView<T> extends AbstractList<T> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0L;

    private final T[] array;
    private final int fromIndex;
    private final int toIndex;

    public ArrayView(T[] array) {
        this.array = array;
        fromIndex = 0;
        toIndex = array.length;
    }

    public ArrayView(T[] array, int fromIndex, int toIndex) {
        Assume.notNegative(toIndex);
        Assume.lesserThanOrEqualTo(toIndex, array.length);
        Objects.checkIndex(fromIndex, toIndex);
        this.array = array;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    public int size() {
        return toIndex - fromIndex;
    }

    @Override
    public T get(int index) {
        return array[toAbsolute(index)];
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new Iterator(fromIndex);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new Iterator(toAbsolute(index));
    }

    private int toAbsolute(int index) {
        int fromIndex = this.fromIndex;
        Objects.checkIndex(index, toIndex - fromIndex);
        return fromIndex + index;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = fromIndex; i < toIndex; i++) {
            action.accept(array[i]);
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(array, fromIndex, toIndex, 0);
    }

    @Override
    public Object[] toArray() {
        if (fromIndex == 0 && toIndex == array.length) {
            return array;
        }
        int size = toIndex - fromIndex;
        Object[] result = new Object[size];
        System.arraycopy(array, fromIndex, result, 0, size);
        return result;
    }

    @Override
    public <U> U[] toArray(U[] typedArray) {
        int size = toIndex - fromIndex;
        if (typedArray.length > size) {
            System.arraycopy(array, fromIndex, typedArray, 0, size);
            return typedArray;
        }
        U[] result = Arrays.emptyCopyOf(typedArray, size);
        System.arraycopy(array, fromIndex, result, 0, size);
        return result;
    }

    @Override
    public <U> U[] toArray(IntFunction<U[]> generator) {
        int size = toIndex - fromIndex;
        U[] result = generator.apply(size);
        System.arraycopy(array, fromIndex, result, 0, size);
        return result;
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException();
    }

    private class Iterator implements ListIterator<T> {
        private int cursor;

        Iterator(int cursor) {
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor != toIndex;
        }

        @Override
        public T next() {
            if (cursor == toIndex) {
                throw new NoSuchElementException();
            }
            return array[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != fromIndex;
        }

        @Override
        public T previous() {
            if (cursor == fromIndex) {
                throw new NoSuchElementException();
            }
            return array[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }
}
