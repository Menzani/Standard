package eu.menzani.data;

import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.HeapBuffer;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOSource extends Source implements Closeable {
    private static final IOSource standardInput = new IOSource(System.in);

    public static IOSource standardInput() {
        return standardInput;
    }

    private static final int defaultBufferSize = 8192 * 2;

    private final InputStream stream;
    private final byte[] buffer;

    private int length;
    private int position;

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(File file) {
        this(file, defaultBufferSize);
    }

    /**
     * Generates garbage while operating.
     */
    public IOSource(Path file) {
        this(file, defaultBufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(Socket socket) {
        this(socket, defaultBufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(URL url) {
        this(url, defaultBufferSize);
    }

    public IOSource(InputStream stream) {
        this(stream, defaultBufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(File file, int bufferSize) {
        try {
            stream = new FileInputStream(file);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        buffer = createBuffer(bufferSize);
    }

    /**
     * Generates garbage while operating.
     */
    public IOSource(Path file, int bufferSize) {
        try {
            stream = Files.newInputStream(file);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        buffer = createBuffer(bufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(Socket socket, int bufferSize) {
        try {
            stream = socket.getInputStream();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        buffer = createBuffer(bufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IOSource(URL url, int bufferSize) {
        try {
            stream = url.openStream();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        buffer = createBuffer(bufferSize);
    }

    public IOSource(InputStream stream, int bufferSize) {
        this.stream = stream;
        buffer = createBuffer(bufferSize);
    }

    private static byte[] createBuffer(int size) {
        return new byte[size];
    }

    @Override
    public boolean hasNext() {
        if (position == length) {
            return read();
        }
        return true;
    }

    private boolean read() {
        try {
            length = stream.read(buffer);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        if (length == -1) {
            return false;
        }
        position = 0;
        return true;
    }

    @Override
    public char next() {
        int position = this.position;
        this.position += 2;
        return HeapBuffer.getChar(buffer, position);
    }

    @Override
    public char peek() {
        return HeapBuffer.getChar(buffer, position);
    }

    @Override
    public void advance() {
        position += 2;
    }

    @Override
    public int position() {
        return position / 2;
    }

    @Override
    public char charAt(int index) {
        return HeapBuffer.getChar(buffer, index * 2);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
