package eu.menzani.collection;

import eu.menzani.struct.Arrays;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class FixedList<T> implements Iterable<T> {
    private final T[] elements;
    private int length;

    public FixedList(int capacity) {
        elements = Arrays.allocateGeneric(capacity);
    }

    public void add(T element) {
        elements[length++] = element;
    }

    public void clear() {
        for (int i = 0; i < length; i++) {
            elements[i] = null;
        }
        length = 0;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < length; i++) {
            action.accept(elements[i]);
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(elements, 0, length, 0);
    }

    private class Iterator implements java.util.Iterator<T> {
        private int index;

        Iterator() {
        }

        @Override
        public boolean hasNext() {
            return index != length;
        }

        @Override
        public T next() {
            return elements[index++];
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            for (int i = index; i < length; i++) {
                action.accept(elements[i]);
            }
        }
    }
}
