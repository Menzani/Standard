package eu.menzani.collection;

import java.util.Iterator;

public interface IterableIterator<T> extends Iterable<T>, Iterator<T> {
    @Override
    default Iterator<T> iterator() {
        return this;
    }
}
