package eu.menzani.struct;

public class Iterables {
    public static int orderIndependentHashCode(Iterable<?> iterable) {
        int result = 0;
        for (Object element : iterable) {
            result += element.hashCode();
        }
        return result;
    }

    public static int hashCode(Iterable<?> iterable) {
        int result = 0;
        for (Object element : iterable) {
            result += element.hashCode();
        }
        return result;
    }
}
