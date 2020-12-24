package eu.menzani.struct;

public abstract class BooleanToggle {
    public abstract boolean toggleTrue();

    public abstract boolean toggleFalse();

    public void ensureTrue(String exceptionMessage) {
        if (!toggleFalse()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public void ensureFalse(String exceptionMessage) {
        if (!toggleTrue()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }
}
