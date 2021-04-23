package eu.menzani.io;

import eu.menzani.lang.BuildableCharArray;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.HeapBuffer;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamBuffer extends BuildableCharArray {
    private final OutputStream stream;
    private byte[] buffer = HeapBuffer.EMPTY;

    public static OutputStreamBuffer standardOutput() {
        return standardOutput(DEFAULT_INITIAL_CAPACITY);
    }

    public static OutputStreamBuffer standardOutput(int initialCapacity) {
        return new OutputStreamBuffer(System.out, initialCapacity);
    }

    public static OutputStreamBuffer standardError() {
        return standardError(DEFAULT_INITIAL_CAPACITY);
    }

    public static OutputStreamBuffer standardError(int initialCapacity) {
        return new OutputStreamBuffer(System.err, initialCapacity);
    }

    public OutputStreamBuffer(OutputStream stream) {
        this(stream, DEFAULT_INITIAL_CAPACITY);
    }

    public OutputStreamBuffer(OutputStream stream, int initialCapacity) {
        super(initialCapacity, false);
        this.stream = stream;
    }

    @Override
    public void flush() {
        int capacity = builder.capacity() * 2;
        if (buffer.length != capacity) {
            buffer = new byte[capacity];
        }

        int j = 0;
        for (int i = 0; i < builder.length(); i++) {
            HeapBuffer.putChar(buffer, j += 2, builder.charAt(i));
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
    protected void flush(char[] buffer, int end) {
        throw new AssertionError();
    }
}
