package eu.menzani.struct;

public abstract class BooleanToggle {
    public abstract boolean setTrue();

    public abstract boolean setFalse();

    public abstract boolean get();

    public void toggleTrue(String exceptionMessage) {
        if (setTrue()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public void toggleFalse(String exceptionMessage) {
        if (setFalse()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public void ensureTrue(String exceptionMessage) {
        if (!get()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public void ensureFalse(String exceptionMessage) {
        if (get()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }
}
