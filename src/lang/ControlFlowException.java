package eu.menzani.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class ControlFlowException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    protected ControlFlowException() {
        super(null, null, false, false);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        throw new AssertionError();
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        throw new AssertionError();
    }
}
