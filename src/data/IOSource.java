package eu.menzani.data;

import eu.menzani.lang.UncaughtException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOSource implements Source, Closeable {
    private final Reader reader;

    public IOSource(Path file) {
        this(file, StandardCharsets.UTF_8);
    }

    public IOSource(Path file, Charset charset) {
        try {
            reader = createReader(Files.newInputStream(file), charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public IOSource(URL url) {
        this(url, StandardCharsets.UTF_8);
    }

    public IOSource(URL url, Charset charset) {
        try {
            reader = createReader(url.openStream(), charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public IOSource(InputStream stream) {
        this(stream, StandardCharsets.UTF_8);
    }

    public IOSource(InputStream stream, Charset charset) {
        reader = createReader(stream, charset);
    }

    private static Reader createReader(InputStream stream, Charset charset) {
        return new InputStreamReader(stream, charset);
    }

    public IOSource(Reader reader) {
        this.reader = reader;
    }

    @Override
    public int fill(char[] buffer) {
        try {
            return reader.read(buffer);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
