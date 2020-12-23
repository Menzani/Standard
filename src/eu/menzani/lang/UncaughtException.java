package eu.menzani.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UncaughtException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final Throwable delegate;

    public UncaughtException(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        delegate = throwable;
        fillInStackTrace();
    }

    public Throwable getThrowable() {
        return delegate;
    }

    @Override
    public String getMessage() {
        return delegate.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return delegate.getLocalizedMessage();
    }

    @Override
    public Throwable getCause() {
        return delegate.getCause();
    }

    @Override
    public Throwable initCause(Throwable cause) {
        return delegate.initCause(cause);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public void printStackTrace() {
        delegate.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        delegate.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        delegate.printStackTrace(s);
    }

    @Override
    public Throwable fillInStackTrace() {
        if (delegate == null) {
            return null;
        }
        return super.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return delegate.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        delegate.setStackTrace(stackTrace);
    }
}
