package eu.menzani.collection;

import java.util.Collections;
import java.util.HashSet;

public class Sets {
    @SafeVarargs
    public static <T> HashSet<T> fromArray(T... elements) {
        HashSet<T> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }
}
