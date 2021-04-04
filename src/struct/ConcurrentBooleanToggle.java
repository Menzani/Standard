package eu.menzani.struct;

import eu.menzani.atomic.AtomicBoolean;
import eu.menzani.lang.Lang;

public class ConcurrentBooleanToggle {
    private static final long VALUE = Lang.objectFieldOffset(ConcurrentBooleanToggle.class, "value");

    private boolean value;

    public void setTrue() {
        AtomicBoolean.setOpaque(this, VALUE, true);
    }

    public void setFalse() {
        AtomicBoolean.setOpaque(this, VALUE, false);
    }

    public boolean get() {
        return AtomicBoolean.getOpaque(this, VALUE);
    }

    public void toggleTrue(String exceptionMessage) {
        if (!AtomicBoolean.compareAndSetVolatile(this, VALUE, false, true)) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public void toggleFalse(String exceptionMessage) {
        if (!AtomicBoolean.compareAndSetVolatile(this, VALUE, true, false)) {
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
