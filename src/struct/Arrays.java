package eu.menzani.struct;

import java.util.Objects;
import java.util.function.Supplier;

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

    @SuppressWarnings("unchecked")
    public static <T> T[] allocateGeneric(int length) {
        return (T[]) new Object[length];
    }

    public static <T> void fill(T[] array, Supplier<? extends T> filler) {
        for (int i = 0; i < array.length; i++) {
            array[i] = filler.get();
        }
    }
}
