package eu.menzani.struct;

import eu.menzani.atomic.AtomicInt;
import eu.menzani.lang.Lang;

public class ConcurrentCounter implements Counter {
    private static final long VALUE = Lang.objectFieldOffset(ConcurrentCounter.class, "value");

    private int value;

    {
        reset();
    }

    @Override
    public int increment() {
        return AtomicInt.getAndIncrementVolatile(this, VALUE);
    }

    @Override
    public void reset() {
        AtomicInt.setOpaque(this, VALUE, 1);
    }
}
