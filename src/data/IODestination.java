package eu.menzani.data;

import eu.menzani.lang.TargetReplacement;
import eu.menzani.lang.UncaughtException;
import eu.menzani.object.GarbageCollectionAware;
import eu.menzani.struct.HeapBuffer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class IODestination extends Destination implements GarbageCollectionAware, Closeable {
    private static final IODestination standardOutput = new IODestination(System.out);
    private static final IODestination standardError = new IODestination(System.err);

    public static IODestination standardOutput() {
        return standardOutput;
    }

    public static IODestination standardError() {
        return standardError;
    }

    private static final int defaultBufferSize = 8192 * 2;

    private final OutputStream stream;
    private final int flushThreshold;

    private byte[] buffer;
    private int index;

    private int temp_usedByReplace;

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(File file) {
        this(file, defaultBufferSize);
    }

    /**
     * Generates garbage while operating.
     */
    public IODestination(Path file) {
        this(file, defaultBufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(Socket socket) {
        this(socket, defaultBufferSize);
    }

    public IODestination(OutputStream stream) {
        this(stream, defaultBufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(File file, int bufferSize) {
        try {
            stream = new FileOutputStream(file);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        flushThreshold = bufferSize;
        buffer = createBuffer(bufferSize);
    }

    /**
     * Generates garbage while operating.
     */
    public IODestination(Path file, int bufferSize) {
        try {
            stream = Files.newOutputStream(file);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        flushThreshold = bufferSize;
        buffer = createBuffer(bufferSize);
    }

    /**
     * Does not generate garbage while operating.
     */
    public IODestination(Socket socket, int bufferSize) {
        try {
            stream = socket.getOutputStream();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        flushThreshold = bufferSize;
        buffer = createBuffer(bufferSize);
    }

    public IODestination(OutputStream stream, int bufferSize) {
        this.stream = stream;
        flushThreshold = bufferSize;
        buffer = createBuffer(bufferSize);
    }

    private static byte[] createBuffer(int size) {
        return new byte[size * 2];
    }

    @Override
    public void append(char character) {
        int index = this.index;
        if (index == buffer.length) {
            buffer = Arrays.copyOf(buffer, index * 2);
        }
        HeapBuffer.putChar(buffer, index, character);
        this.index += 2;
    }

    @Override
    public void append(java.lang.String string) {
        int length = string.length();
        int newIndex = index + length;
        if (newIndex >= buffer.length) {
            buffer = Arrays.copyOf(buffer, newIndex * 2);
        }
        int j = index;
        for (int i = 0; i < length; ) {
            char character = string.charAt(i++);
            if (shouldReplace()) {
                newIndex = replace(character, newIndex, j);
                j = temp_usedByReplace;
            } else {
                HeapBuffer.putChar(buffer, j, character);
                j += 2;
            }
        }
        assert j == newIndex;
        index = newIndex;
    }

    private int replace(char character, int newIndex, int j) {
        for (TargetReplacement targetReplacement : getTargetReplacements()) {
            if (character == targetReplacement.getTarget()) {
                CharSequence replacement = targetReplacement.getReplacement();
                int replacementLength = replacement.length();
                newIndex += replacementLength - 1;
                if (newIndex >= buffer.length) {
                    buffer = Arrays.copyOf(buffer, newIndex * 2);
                }
                for (int k = 0; k < replacementLength; j += 2) {
                    HeapBuffer.putChar(buffer, j, replacement.charAt(k++));
                }
                break;
            }
        }
        temp_usedByReplace = j;
        return newIndex;
    }

    @Override
    public void append(StringBuilder builder) {
        int length = builder.length();
        int newIndex = index + length;
        if (newIndex >= buffer.length) {
            buffer = Arrays.copyOf(buffer, newIndex * 2);
        }
        int j = index;
        for (int i = 0; i < length; ) {
            char character = builder.charAt(i++);
            if (shouldReplace()) {
                newIndex = replace(character, newIndex, j);
                j = temp_usedByReplace;
            } else {
                HeapBuffer.putChar(buffer, j, character);
                j += 2;
            }
        }
        assert j == newIndex;
        index = newIndex;
    }

    @Override
    public void deleteLastChar() {
        index -= 2;
    }

    @Override
    public void checkFlush() {
        if (index > flushThreshold) {
            flush();
        }
    }

    @Override
    public void flush() {
        try {
            stream.write(buffer, 0, index);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        index = 0;
    }

    @Override
    public void gc() {
        buffer = createBuffer(flushThreshold);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
