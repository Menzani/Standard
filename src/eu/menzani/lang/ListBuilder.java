package eu.menzani.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListBuilder<T, U> {
    private final T[] from;
    private final U[] to;

    @SuppressWarnings("unchecked")
    public ListBuilder(T[] array) {
        from = array;
        to = (U[]) new Object[array.length];
    }

    public void map(Mapper<? super T, ? extends U> mapper) {
        for (int i = 0; i < to.length; i++) {
            to[i] = mapper.apply(from[i]);
        }
    }

    public List<U> build() {
        return ListBuilder.fromArray(to);
    }

    public List<U> buildUnmodifiable() {
        return Collections.unmodifiableList(build());
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
