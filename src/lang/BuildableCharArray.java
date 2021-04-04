package eu.menzani.lang;

import eu.menzani.object.GarbageCollectionAware;
import eu.menzani.struct.Strings;

public abstract class BuildableCharArray implements GarbageCollectionAware {
    protected static final int DEFAULT_INITIAL_CAPACITY = 512;

    private char[] buffer;

    @View
    public StringBuilder builder;

    protected BuildableCharArray(int initialCapacity) {
        buffer = new char[initialCapacity];
        builder = new StringBuilder(initialCapacity);
    }

    public void println() {
        builder.append(Strings.LN);
        flush();
    }

    public void flush() {
        int capacity = builder.capacity();
        if (buffer.length != capacity) {
            buffer = new char[capacity];
        }

        int length = builder.length();
        builder.getChars(0, length, buffer, 0);
        builder.setLength(0);

        flush(buffer, length);
    }

    protected abstract void flush(char[] buffer, int end);

    @Override
    public void gc() {
        builder = new StringBuilder(builder);
        buffer = new char[builder.capacity()];
    }
}
