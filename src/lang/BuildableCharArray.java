package eu.menzani.lang;

import eu.menzani.object.GarbageCollectionAware;

public abstract class BuildableCharArray implements GarbageCollectionAware {
    protected static final int DEFAULT_INITIAL_CAPACITY = 512;

    private final int initialCapacity;
    private char[] buffer;

    @View
    public StringBuilder builder;

    protected BuildableCharArray(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        gc();
    }

    public void println() {
        builder.append(System.lineSeparator());
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
        buffer = new char[initialCapacity];
        builder = new StringBuilder(initialCapacity);
    }
}
