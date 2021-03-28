package eu.menzani.data;

import eu.menzani.lang.View;
import eu.menzani.object.Allocator;

public class String extends Element implements Comparable<String> {
    private static final Allocator<String> allocator = Allocator.create(String::new);

    private StringBuilder value;

    public static String allocate() {
        String instance = allocator.allocate();
        instance.clear();
        return instance;
    }

    public static String allocate(java.lang.String value) {
        String instance = allocate();
        instance.value.append(value);
        return instance;
    }

    public static String allocate(CharSequence value) {
        String instance = allocate();
        instance.value.append(value);
        return instance;
    }

    private String() {
        gc();
    }

    public java.lang.String asJavaString() {
        return value.toString();
    }

    @View
    public CharSequence asCharSequence() {
        return value;
    }

    public StringBuilder set() {
        return value;
    }

    public void clear() {
        value.setLength(0);
    }

    @Override
    public int compareTo(String other) {
        return value.compareTo(other.value);
    }

    @Override
    public void gc() {
        value = new StringBuilder();
    }

    @Override
    public void deallocate() {
        allocator.deallocate(this);
    }
}
