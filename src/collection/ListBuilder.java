package eu.menzani.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListBuilder<F, T> extends CollectionBuilder<F> {
    private final List<T> to;

    public ListBuilder(Collection<F> from) {
        super(from);
        to = new ArrayList<>(from.size());
    }

    public ListBuilder(F[] from) {
        super(from);
        to = new ArrayList<>(from.length);
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
}
