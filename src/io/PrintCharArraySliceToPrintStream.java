package eu.menzani.io;

import eu.menzani.lang.Field;
import eu.menzani.lang.MethodHandles;
import eu.menzani.lang.UncaughtException;

import java.io.BufferedWriter;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;

public class PrintCharArraySliceToPrintStream {
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

    public PrintCharArraySliceToPrintStream(PrintStream stream) {
        this.stream = stream;
        synchronized (PrintStream_textOut) {
            PrintStream_textOut.setTargetInstance(stream);
            PrintStream_charOut.setTargetInstance(stream);
            stream_textOut = PrintStream_textOut.getValue();
            stream_charOut = PrintStream_charOut.getValue();
        }
    }

    public void print(char[] cbuf, int off, int len) {
        try {
            synchronized (stream) {
                stream_textOut.write(cbuf, off, len);
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
