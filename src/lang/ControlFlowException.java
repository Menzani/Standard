package eu.menzani.lang;

public abstract class ControlFlowException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    protected ControlFlowException() {
        super(null, null, true, false);
    }
}
