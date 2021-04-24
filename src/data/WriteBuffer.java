package eu.menzani.data;

import eu.menzani.object.GarbageCollectionAware;

public class WriteBuffer implements GarbageCollectionAware {
    private final int size;
    private final Destination destination;

    private StringBuilder builder;
    private char[] buffer;

    public WriteBuffer(Destination destination) {
        this(8192, destination);
    }

    public WriteBuffer(int size, Destination destination) {
        this.size = size;
        this.destination = destination;

        builder = new StringBuilder(size *= 2);
        buffer = new char[size];
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public int position() {
        return builder.length();
    }

    public void checkFull() {
        if (builder.length() > size) {
            flush();
        }
    }

    public void flush() {
        int capacity = builder.capacity();
        if (buffer.length != capacity) {
            buffer = new char[capacity];
        }

        int length = builder.length();
        builder.getChars(0, length, buffer, 0);
        builder.setLength(0);

        destination.send(buffer, length);
    }

    @Override
    public void gc() {
        builder = new StringBuilder(builder);
        buffer = new char[builder.capacity()];
    }
}
