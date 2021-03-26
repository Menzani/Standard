package eu.menzani.lang;

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
