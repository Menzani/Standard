package eu.menzani.io;

import eu.menzani.lang.BuildableCharArray;

import java.io.PrintStream;

public class PrintStreamBuffer extends BuildableCharArray {
    private final PrintCharArraySliceToPrintStream stream;

    public static PrintStreamBuffer standardOutput() {
        return standardOutput(DEFAULT_INITIAL_CAPACITY);
    }

    public static PrintStreamBuffer standardOutput(int initialCapacity) {
        return new PrintStreamBuffer(System.out, initialCapacity);
    }

    public static PrintStreamBuffer standardError() {
        return standardError(DEFAULT_INITIAL_CAPACITY);
    }

    public static PrintStreamBuffer standardError(int initialCapacity) {
        return new PrintStreamBuffer(System.err, initialCapacity);
    }

    public PrintStreamBuffer(PrintStream stream) {
        this(stream, DEFAULT_INITIAL_CAPACITY);
    }

    public PrintStreamBuffer(PrintStream stream, int initialCapacity) {
        super(initialCapacity);
        this.stream = new PrintCharArraySliceToPrintStream(stream);
    }

    @Override
    protected void flush(char[] buffer, int end) {
        stream.print(buffer, 0, end);
    }
}
