package eu.menzani.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListBuilder<F, T> {
    private final F[] from;
    private final List<T> to;

    public ListBuilder(F[] from) {
        this.from = from;
        to = new ArrayList<>(from.length);
    }

    public F[] array() {
        return from;
    }

    public void add(T element) {
        to.add(element);
    }

    public List<T> build() {
        return to;
    }

    public List<T> buildUnmodifiable() {
        return Collections.unmodifiableList(to);
    }

    @SafeVarargs
    public static <T> ArrayList<T> fromArray(T... elements) {
        ArrayList<T> list = new ArrayList<>(elements.length);
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
}
