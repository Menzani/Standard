package eu.menzani.io;

import eu.menzani.error.GlobalExceptionHandler;
import eu.menzani.lang.ControlFlowException;

public class PrintToConsoleException extends ControlFlowException implements Runnable {
    static {
        GlobalExceptionHandler.addIgnored(PrintToConsoleException.class);
    }

    private static final long serialVersionUID = 0L;

    private final String value;

    public PrintToConsoleException(String value) {
        this.value = value;
    }

    @Override
    public void printStackTrace() {
        System.err.println(value);
    }

    @Override
    public void run() {
        printStackTrace();
    }
}
