package eu.menzani.misc;

import java.util.Objects;

public class Arrays {
    public static int orderIndependentHashCode(Object[] array) {
        int result = 0;
        for (Object element : array) {
            result += Objects.hashCode(element);
        }
        return result;
    }
}
