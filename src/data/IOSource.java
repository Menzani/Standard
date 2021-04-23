package eu.menzani.data;

import eu.menzani.io.InputStreamReader;
import eu.menzani.lang.UncaughtException;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOSource implements Source, Closeable {
    private static final IOSource standardInput = new IOSource(System.in);

    public static IOSource standardInput() {
        return standardInput;
    }

    private final Reader reader;

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(File file) {
        try {
            reader = createReader(new FileInputStream(file));
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    /**
     * Generates garbage while operating.
     */
    public IOSource(Path file) {
        try {
            reader = createReader(Files.newInputStream(file));
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(Socket socket) {
        try {
            reader = createReader(socket.getInputStream());
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(URL url) {
        try {
            reader = createReader(url.openStream());
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public IOSource(InputStream stream) {
        reader = createReader(stream);
    }

    private static Reader createReader(InputStream stream) {
        return new InputStreamReader(stream);
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
