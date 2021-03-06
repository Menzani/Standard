package eu.menzani.struct;

import eu.menzani.atomic.AtomicBoolean;
import eu.menzani.lang.Lang;

public class ConcurrentBooleanToggle extends BooleanToggle {
    private static final long VALUE = Lang.objectFieldOffset(ConcurrentBooleanToggle.class, "value");

    private boolean value;

    @Override
    public boolean setTrue() {
        return !AtomicBoolean.compareAndSetVolatile(this, VALUE, false, true);
    }

    @Override
    public boolean setFalse() {
        return !AtomicBoolean.compareAndSetVolatile(this, VALUE, true, false);
    }

    @Override
    public boolean get() {
        return AtomicBoolean.getOpaque(this, VALUE);
    }
}
