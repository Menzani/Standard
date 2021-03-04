package eu.menzani.lang;

import eu.menzani.error.GlobalExceptionHandler;
import eu.menzani.error.HandledThrowable;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UncaughtException extends RuntimeException implements HandledThrowable {
    static {
        GlobalExceptionHandler.register();
    }

    private static final long serialVersionUID = 0L;

    private final Throwable uncaught;

    public UncaughtException(Throwable uncaught) {
        super(null, null, true, false);
        if (uncaught instanceof Error) {
            throw (Error) uncaught;
        }
        if (uncaught instanceof RuntimeException) {
            throw (RuntimeException) uncaught;
        }
        this.uncaught = uncaught;
    }

    public Throwable getUncaught() {
        return uncaught;
    }

    @Override
    public String getMessage() {
        return uncaught.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return uncaught.getLocalizedMessage();
    }

    @Override
    public Throwable getCause() {
        return uncaught.getCause();
    }

    @Override
    public Throwable initCause(Throwable cause) {
        return uncaught.initCause(cause);
    }

    @Override
    public String toString() {
        return uncaught.toString();
    }

    @Override
    public void printStackTrace() {
        uncaught.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        uncaught.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        uncaught.printStackTrace(s);
    }

    @Override
    public Throwable fillInStackTrace() {
        throw new AssertionError();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return uncaught.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        uncaught.setStackTrace(stackTrace);
    }

    @Override
    public void run() {
        for (Throwable suppressed : getSuppressed()) {
            uncaught.addSuppressed(suppressed);
        }
    }
}
