package eu.menzani.data;

import eu.menzani.lang.UncaughtException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IODestination implements Destination, Closeable {
    private final Writer writer;

    public IODestination(Path file) {
        this(file, StandardCharsets.UTF_8);
    }

    public IODestination(Path file, Charset charset) {
        try {
            writer = createWriter(Files.newOutputStream(file), charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public IODestination(OutputStream stream) {
        this(stream, StandardCharsets.UTF_8);
    }

    public IODestination(OutputStream stream, Charset charset) {
        writer = createWriter(stream, charset);
    }

    private static Writer createWriter(OutputStream stream, Charset charset) {
        return new OutputStreamWriter(stream, charset);
    }

    public IODestination(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void send(char[] buffer, int end) {
        try {
            writer.write(buffer, 0, end);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
