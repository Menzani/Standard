package eu.menzani.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder<F, T> {
    private final F[] from;
    private final Set<T> to;

    public SetBuilder(F[] from) {
        this.from = from;
        to = new HashSet<>(from.length);
    }

    public F[] array() {
        return from;
    }

    public void add(T element) {
        to.add(element);
    }

    public Set<T> build() {
        return to;
    }

    public Set<T> buildUnmodifiable() {
        return Collections.unmodifiableSet(to);
    }

    @SafeVarargs
    public static <T> HashSet<T> fromArray(T... elements) {
        HashSet<T> set = new HashSet<>(elements.length);
        for (T element : elements) {
            set.add(element);
        }
        return set;
    }
}
