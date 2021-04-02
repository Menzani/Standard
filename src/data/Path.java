package eu.menzani.data;

import eu.menzani.struct.Strings;

public class Path {
    private final java.lang.String[] strings;
    private final int[] ints;

    public Path(java.lang.String path) {
        java.lang.String[] elements = path.split("\\.");
        int length = elements.length;
        strings = new java.lang.String[length];
        ints = new int[length];
        int i = 0;
        for (java.lang.String element : elements) {
            if (Strings.isInteger(element)) {
                ints[i] = java.lang.Integer.parseInt(element);
                strings[i] = null;
            } else {
                strings[i] = element;
                ints[i] = -1;
            }
            i++;
        }
        assert i == length;
    }

    int length() {
        return strings.length;
    }

    java.lang.String getString(int i) {
        return strings[i];
    }

    int getInt(int i) {
        return ints[i];
    }

    boolean isString(int i) {
        return ints[i] == -1;
    }
}
