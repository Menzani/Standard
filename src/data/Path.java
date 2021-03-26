package eu.menzani.data;

import eu.menzani.collection.ResizableArray;
import eu.menzani.collection.ResizableIntArray;

public class Path {
    public static Builder of(java.lang.String element) {
        return new Builder().then(element);
    }

    public static Builder of(int element) {
        return new Builder().then(element);
    }

    private final java.lang.String[] strings;
    private final int[] ints;

    Path(Builder builder) {
        strings = builder.strings.asFixedArray();
        ints = builder.ints.asFixedArray();
    }

    java.lang.String[] getStrings() {
        return strings;
    }

    int[] getInts() {
        return ints;
    }

    static boolean isVoid(java.lang.String string) {
        return string == EMPTY_STRING;
    }

    static boolean isVoid(int i) {
        return i == EMPTY_INT;
    }

    static final java.lang.String EMPTY_STRING = null;
    static final int EMPTY_INT = -1;

    public static class Builder {
        private final ResizableArray<java.lang.String> strings = new ResizableArray<>(java.lang.String.class);
        private final ResizableIntArray ints = new ResizableIntArray();

        public Builder then(java.lang.String element) {
            strings.add(element);
            ints.add(EMPTY_INT);
            return this;
        }

        public Builder then(int element) {
            strings.add(EMPTY_STRING);
            ints.add(element);
            return this;
        }

        public Path build() {
            return new Path(this);
        }
    }
}
