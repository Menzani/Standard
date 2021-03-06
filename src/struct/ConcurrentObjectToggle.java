package eu.menzani.struct;

import eu.menzani.atomic.Atomic;
import eu.menzani.lang.Lang;

public class ConcurrentObjectToggle<T> extends ObjectToggle<T> {
    private static final long VALUE, LAST_SET_VALUE;

    static {
        final Class<?> clazz = ConcurrentObjectToggle.class;
        VALUE = Lang.objectFieldOffset(clazz, "value");
        LAST_SET_VALUE = Lang.objectFieldOffset(clazz, "lastSetValue");
    }

    private static final Object placeholder = new Object();

    private Object value;
    private T lastSetValue;

    @Override
    public boolean set() {
        return Atomic.compareAndSetVolatile(this, VALUE, null, placeholder);
    }

    @Override
    public void set(T value) {
        Atomic.setRelease(this, VALUE, value);
    }

    @Override
    public boolean unset() {
        Object value = Atomic.getAndSetVolatile(this, VALUE, null);
        if (value == null) {
            return false;
        }
        if (value == placeholder) {
            do {
                value = Atomic.getAndSetVolatile(this, VALUE, null);
            } while (value == null);
        }
        Atomic.setRelease(this, LAST_SET_VALUE, value);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        Object value;
        do {
            value = Atomic.getAcquire(this, VALUE);
        } while (value == placeholder);
        if (value == null) {
            return Atomic.getAcquire(this, LAST_SET_VALUE);
        }
        return (T) value;
    }
}
