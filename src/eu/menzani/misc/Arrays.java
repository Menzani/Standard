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

    public static boolean contains(Object[] array, Object element) {
        return indexOf(array, element) != -1;
    }

    public static int indexOf(Object[] array, Object element) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], element)) {
                return i;
            }
        }
        return -1;
    }
}
