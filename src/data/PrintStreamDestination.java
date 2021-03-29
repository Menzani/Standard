package eu.menzani.data;

import eu.menzani.io.PrintCharArraySliceToPrintStream;

import java.io.PrintStream;

public class PrintStreamDestination implements Destination {
    private final PrintCharArraySliceToPrintStream stream;

    public static PrintStreamDestination standardOutput() {
        return new PrintStreamDestination(System.out);
    }

    public static PrintStreamDestination standardError() {
        return new PrintStreamDestination(System.err);
    }

    public PrintStreamDestination(PrintStream stream) {
        this.stream = new PrintCharArraySliceToPrintStream(stream);
    }

    @Override
    public void send(char[] buffer, int end) {
        stream.print(buffer, 0, end);
    }
}
