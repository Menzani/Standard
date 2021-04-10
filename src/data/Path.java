package eu.menzani.data;

import eu.menzani.lang.CharSequenceSplitter;

public class Path {
    private static final CharSequenceSplitter splitter = new CharSequenceSplitter('.', null);

    private final java.lang.String[] keys;

    public Path(java.lang.String path) {
        keys = splitter.split(path);
        if (keys == null) {
            throw new IllegalArgumentException("path must contain at least one key.");
        }
    }

    java.lang.String[] getKeys() {
        return keys;
    }
}
