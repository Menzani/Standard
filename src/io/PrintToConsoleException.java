package eu.menzani.io;

import eu.menzani.error.GlobalExceptionHandler;
import eu.menzani.lang.ControlFlowException;

public class PrintToConsoleException extends ControlFlowException {
    static {
        GlobalExceptionHandler.addIgnored(PrintToConsoleException.class);
    }

    private static final long serialVersionUID = 0L;

    public PrintToConsoleException(String value) {
        System.err.println(value);
    }

    @Override
    public void printStackTrace() {
    }
}
