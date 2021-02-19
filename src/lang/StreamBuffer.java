package eu.menzani.lang;

import java.io.PrintStream;

public class StreamBuffer {
    private static final int defaultInitialCapacity = 512;

    private final PrintStream stream;
    private char[] buffer;

    public final StringBuilder builder;

    public static StreamBuffer standardOutput() {
        return standardOutput(defaultInitialCapacity);
    }

    public static StreamBuffer standardOutput(int initialCapacity) {
        return new StreamBuffer(System.out, initialCapacity);
    }

    public static StreamBuffer standardError() {
        return standardError(defaultInitialCapacity);
    }

    public static StreamBuffer standardError(int initialCapacity) {
        return new StreamBuffer(System.err, initialCapacity);
    }

    public StreamBuffer(PrintStream stream) {
        this(stream, defaultInitialCapacity);
    }

    public StreamBuffer(PrintStream stream, int initialCapacity) {
        this.stream = stream;
        buffer = new char[initialCapacity];
        builder = new StringBuilder(initialCapacity);
    }

    public void reset() {
        builder.setLength(0);
    }

    public void println() {
        builder.append(System.lineSeparator());
        print();
    }

    public void print() {
        int capacity = builder.capacity();
        if (buffer.length != capacity) {
            buffer = new char[capacity];
        }
        builder.getChars(0, builder.length(), buffer, 0);

        stream.print(buffer);
    }
}
