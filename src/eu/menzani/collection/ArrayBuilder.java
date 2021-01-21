package eu.menzani.collection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class ArrayBuilder<F, T> {
    private final Collection<F> fromCollection;
    private final F[] fromArray;
    private final T[] to;
    private int toIndex;

    public ArrayBuilder(Collection<F> from, Class<T> toElementType) {
        fromCollection = from;
        fromArray = null;
        to = createTo(toElementType, from.size());
    }

    public ArrayBuilder(F[] from, Class<T> toElementType) {
        fromArray = from;
        fromCollection = null;
        to = createTo(toElementType, from.length);
    }

    @SuppressWarnings("unchecked")
    private T[] createTo(Class<T> toElementType, int length) {
        return (T[]) Array.newInstance(toElementType, length);
    }

    public Collection<F> collection() {
        return fromCollection;
    }

    public F[] array() {
        return fromArray;
    }

    public void add(T element) {
        to[toIndex++] = element;
    }

    public T[] build() {
        return Arrays.copyOf(to, toIndex);
    }
}
