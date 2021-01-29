package eu.menzani.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder<F, T> extends CollectionBuilder<F> {
    private final Set<T> to;

    public SetBuilder(Collection<F> from) {
        super(from);
        to = new HashSet<>(from.size());
    }

    public SetBuilder(F[] from) {
        super(from);
        to = new HashSet<>(from.length);
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
