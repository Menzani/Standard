package eu.menzani.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayBuilder<T, U> {
    private final List<T> from;
    private final List<U> to;
    private final Class<U> elementType;

    public ArrayBuilder(Class<U> elementType) {
        from = new ArrayList<>();
        to = new ArrayList<>();
        this.elementType = elementType;
    }

    public ArrayBuilder(int capacity, Class<U> elementType) {
        from = new ArrayList<>(capacity);
        to = new ArrayList<>(capacity);
        this.elementType = elementType;
    }

    public ArrayBuilder(T[] array, Class<U> elementType) {
        from = ListBuilder.fromArray(array);
        to = new ArrayList<>(array.length);
        this.elementType = elementType;
    }

    public ArrayBuilder(List<T> list, Class<U> elementType) {
        from = list;
        to = new ArrayList<>(list.size());
        this.elementType = elementType;
    }

    public void add(T element) {
        from.add(element);
    }

    public boolean filter(Filter<? super T> filter) {
        return from.removeIf(filter);
    }

    public void map(Mapper<? super T, ? extends U> mapper) {
        for (T element : from) {
            U newElement = mapper.apply(element);
            to.add(newElement);
        }
    }

    public U[] build() {
        @SuppressWarnings("unchecked") var dummy = (U[]) Array.newInstance(elementType, 0);
        return to.toArray(dummy);
    }
}
