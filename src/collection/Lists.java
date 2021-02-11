package eu.menzani.collection;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {
    @SafeVarargs
    public static <T> ArrayList<T> fromArray(T... elements) {
        ArrayList<T> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }
}
