package eu.menzani.data;

import eu.menzani.io.PrintCharArraySliceToPrintStream;

import java.io.PrintStream;

public class PrintStreamDestination implements Destination {
    private static final PrintStreamDestination standardOutput = new PrintStreamDestination(System.out);
    private static final PrintStreamDestination standardError = new PrintStreamDestination(System.err);

    public static PrintStreamDestination standardOutput() {
        return standardOutput;
    }

    public static PrintStreamDestination standardError() {
        return standardError;
    }

    private final PrintCharArraySliceToPrintStream stream;

    public PrintStreamDestination(PrintStream stream) {
        this.stream = new PrintCharArraySliceToPrintStream(stream);
    }

    @Override
    public void send(char[] buffer, int end) {
        stream.print(buffer, 0, end);
    }
}
