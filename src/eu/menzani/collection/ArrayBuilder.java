package eu.menzani.collection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class ArrayBuilder<F, T> extends CollectionBuilder<F> {
    private final T[] to;
    private int toIndex;

    public ArrayBuilder(Collection<F> from, Class<T> toElementType) {
        super(from);
        to = createTo(toElementType, from.size());
    }

    public ArrayBuilder(F[] from, Class<T> toElementType) {
        super(from);
        to = createTo(toElementType, from.length);
    }

    @SuppressWarnings("unchecked")
    private T[] createTo(Class<T> toElementType, int length) {
        return (T[]) Array.newInstance(toElementType, length);
    }

    public void add(T element) {
        to[toIndex++] = element;
    }

    public T[] build() {
        return Arrays.copyOf(to, toIndex);
    }
}
