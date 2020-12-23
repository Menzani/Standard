package eu.menzani.misc;

import eu.menzani.atomic.AtomicBoolean;
import eu.menzani.lang.Lang;

public class ConcurrentBooleanToggle extends BooleanToggle {
    private static final long VALUE = Lang.objectFieldOffset(ConcurrentBooleanToggle.class, "value");

    private boolean value;

    @Override
    public boolean toggleTrue() {
        return AtomicBoolean.compareAndSetVolatile(this, VALUE, false, true);
    }

    @Override
    public boolean toggleFalse() {
        return AtomicBoolean.compareAndSetVolatile(this, VALUE, true, false);
    }
}
