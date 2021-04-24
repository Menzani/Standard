package eu.menzani.io;

import eu.menzani.lang.UncaughtException;
import eu.menzani.lang.View;
import eu.menzani.object.GarbageCollectionAware;
import eu.menzani.struct.HeapBuffer;
import eu.menzani.struct.Strings;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamBuffer implements GarbageCollectionAware {
    private static final int defaultInitialCapacity = 512;

    private final OutputStream stream;
    private byte[] buffer;

    @View
    public StringBuilder builder;

    public static OutputStreamBuffer standardOutput() {
        return standardOutput(defaultInitialCapacity);
    }

    public static OutputStreamBuffer standardOutput(int initialCapacity) {
        return new OutputStreamBuffer(System.out, initialCapacity);
    }

    public static OutputStreamBuffer standardError() {
        return standardError(defaultInitialCapacity);
    }

    public static OutputStreamBuffer standardError(int initialCapacity) {
        return new OutputStreamBuffer(System.err, initialCapacity);
    }

    public OutputStreamBuffer(OutputStream stream) {
        this(stream, defaultInitialCapacity);
    }

    public OutputStreamBuffer(OutputStream stream, int initialCapacity) {
        this.stream = stream;
        builder = new StringBuilder(initialCapacity);
        buffer = new byte[initialCapacity * 2];
    }

    public void println() {
        builder.append(Strings.LN);
        flush();
    }

    public void flush() {
        int capacity = builder.capacity() * 2;
        if (buffer.length != capacity) {
            buffer = new byte[capacity];
        }

        int j = 0;
        for (int i = 0; i < builder.length(); j += 2) {
            HeapBuffer.putChar(buffer, j, builder.charAt(i++));
        }
        assert j == capacity;
        builder.setLength(0);

        try {
            stream.write(buffer, 0, capacity);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    @Override
    public void gc() {
        builder = new StringBuilder(builder);
        buffer = new byte[builder.capacity() * 2];
    }
}
