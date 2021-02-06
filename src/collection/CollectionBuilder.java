package eu.menzani.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

abstract class CollectionBuilder<F> implements Iterable<F> {
    private final Iterable<F> from;

    CollectionBuilder(Collection<F> from) {
        this.from = from;
    }

    CollectionBuilder(F[] from) {
        this.from = new ArrayView<>(from);
    }

    @Override
    public Iterator<F> iterator() {
        return from.iterator();
    }

    @Override
    public void forEach(Consumer<? super F> action) {
        from.forEach(action);
    }

    @Override
    public Spliterator<F> spliterator() {
        return from.spliterator();
    }
}
