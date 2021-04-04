package eu.menzani.collection;

import eu.menzani.struct.Arrays;

import java.util.Collection;

public class ArrayBuilder<F, T> extends CollectionBuilder<F> {
    private final T[] to;
    private int length;

    public ArrayBuilder(Collection<F> from, Class<T> toElementType) {
        super(from);
        to = Arrays.allocateGeneric(toElementType, from.size());
    }

    public ArrayBuilder(F[] from, Class<T> toElementType) {
        super(from);
        to = Arrays.allocateGeneric(toElementType, from.length);
    }

    public void add(T element) {
        to[length++] = element;
    }

    public T[] build() {
        return java.util.Arrays.copyOf(to, length);
    }
}
