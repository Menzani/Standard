package eu.menzani.lang;

import java.io.BufferedWriter;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;

public class StreamBuffer extends BuildableCharArray {
    private static final Field<BufferedWriter> PrintStream_textOut;
    private static final Field<OutputStreamWriter> PrintStream_charOut;
    private static final MethodHandle BufferedWriter_flushBuffer =
            MethodHandles.ofVirtualPrivate(BufferedWriter.class, "flushBuffer", MethodHandles.VOID_VOID_METHOD_TYPE);
    private static final MethodHandle OutputStreamWriter_flushBuffer =
            MethodHandles.ofVirtualPrivate(OutputStreamWriter.class, "flushBuffer", MethodHandles.VOID_VOID_METHOD_TYPE);

    static {
        final Class<?> clazz = PrintStream.class;
        PrintStream_textOut = Field.of(clazz, "textOut");
        PrintStream_charOut = Field.of(clazz, "charOut");
        PrintStream_textOut.setAccessible();
        PrintStream_charOut.setAccessible();
    }

    private final Object stream;
    private final BufferedWriter stream_textOut;
    private final OutputStreamWriter stream_charOut;

    public static StreamBuffer standardOutput() {
        return standardOutput(DEFAULT_INITIAL_CAPACITY);
    }

    public static StreamBuffer standardOutput(int initialCapacity) {
        return new StreamBuffer(System.out, initialCapacity);
    }

    public static StreamBuffer standardError() {
        return standardError(DEFAULT_INITIAL_CAPACITY);
    }

    public static StreamBuffer standardError(int initialCapacity) {
        return new StreamBuffer(System.err, initialCapacity);
    }

    public StreamBuffer(PrintStream stream) {
        this(stream, DEFAULT_INITIAL_CAPACITY);
    }

    public StreamBuffer(PrintStream stream, int initialCapacity) {
        super(initialCapacity);
        this.stream = stream;
        synchronized (PrintStream_textOut) {
            PrintStream_textOut.setTargetInstance(stream);
            PrintStream_charOut.setTargetInstance(stream);
            stream_textOut = PrintStream_textOut.getValue();
            stream_charOut = PrintStream_charOut.getValue();
        }
    }

    @Override
    protected void flush(char[] buffer, int end) {
        try {
            synchronized (stream) {
                stream_textOut.write(buffer, 0, end);
                BufferedWriter_flushBuffer.invokeExact(stream_textOut);
                OutputStreamWriter_flushBuffer.invokeExact(stream_charOut);
            }
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
        } catch (Throwable e) {
            throw new UncaughtException(e);
        }
    }
}
