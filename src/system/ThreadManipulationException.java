package eu.menzani.system;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Ensure;

public class ThreadManipulationException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final int errorCode;

    ThreadManipulationException(String message) {
        super(message);
        errorCode = 0;
    }

    ThreadManipulationException(int errorCode) {
        super("Error code = " + errorCode);
        Assume.notZero(errorCode);
        this.errorCode = errorCode;
    }

    public boolean hasErrorCode() {
        return errorCode != 0;
    }

    public int getErrorCode() {
        Ensure.notZero(errorCode);
        return errorCode;
    }
}
