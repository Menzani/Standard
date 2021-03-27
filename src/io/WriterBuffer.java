package eu.menzani.io;

import eu.menzani.lang.BuildableCharArray;
import eu.menzani.lang.UncaughtException;

import java.io.IOException;
import java.io.Writer;

public class WriterBuffer extends BuildableCharArray {
    private final Writer writer;

    public WriterBuffer(Writer writer) {
        this(writer, DEFAULT_INITIAL_CAPACITY);
    }

    public WriterBuffer(Writer writer, int initialCapacity) {
        super(initialCapacity);
        this.writer = writer;
    }

    @Override
    protected void flush(char[] buffer, int end) {
        try {
            writer.write(buffer, 0, end);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }
}
