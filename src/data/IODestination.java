package eu.menzani.data;

import eu.menzani.io.OutputStreamWriter;
import eu.menzani.lang.UncaughtException;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class IODestination implements Destination, Closeable {
    private static final IODestination standardOutput = new IODestination(System.out);
    private static final IODestination standardError = new IODestination(System.err);

    public static IODestination standardOutput() {
        return standardOutput;
    }

    public static IODestination standardError() {
        return standardError;
    }

    private final Writer writer;

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(File file) {
        try {
            writer = createWriter(new FileOutputStream(file));
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    /**
     * Generates garbage while operating.
     */
    public IODestination(Path file) {
        try {
            writer = createWriter(Files.newOutputStream(file));
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(Socket socket) {
        try {
            writer = createWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public IODestination(OutputStream stream) {
        writer = createWriter(stream);
    }

    private static Writer createWriter(OutputStream stream) {
        return new OutputStreamWriter(stream);
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
