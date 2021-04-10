package eu.menzani.struct;

import eu.menzani.object.ObjectFactory;

import java.lang.reflect.Array;
import java.util.Objects;

public class Arrays {
    public static String toString(Object[] array, int from, int to) {
        if (to == from) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        while (true) {
            builder.append(array[from]);
            if (++from == to) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static int hashCode(Object[] array, int from, int to) {
        int result = 1;
        for (; from < to; from++) {
            result *= 31;
            Object element = array[from];
            if (element != null) {
                result += element.hashCode();
            }
        }
        return result;
    }

    public static int orderIndependentHashCode(Object[] array) {
        int result = 0;
        for (Object element : array) {
            result += element.hashCode();
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

    @SuppressWarnings("unchecked")
    public static <T> T[] allocateGeneric(Class<? super T> elementType, int length) {
        return (T[]) Array.newInstance(elementType, length);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] emptyCopyOf(T[] array, int length) {
        return (T[]) Array.newInstance(array.getClass().getComponentType(), length);
    }

    public static <T> void fill(T[] array, ObjectFactory<T> filler) {
        for (int i = 0; i < array.length; i++) {
            array[i] = filler.newInstance();
        }
    }
}
