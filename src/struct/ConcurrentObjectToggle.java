package eu.menzani.struct;

import eu.menzani.atomic.Atomic;
import eu.menzani.lang.Lang;

public class ConcurrentObjectToggle<T> {
    private static final long VALUE, LAST_SET_VALUE;

    static {
        final Class<?> clazz = ConcurrentObjectToggle.class;
        VALUE = Lang.objectFieldOffset(clazz, "value");
        LAST_SET_VALUE = Lang.objectFieldOffset(clazz, "lastSetValue");
    }

    private static final Object placeholder = new Object();

    private Object value;
    private T lastSetValue;

    public boolean set() {
        return Atomic.compareAndSetVolatile(this, VALUE, null, placeholder);
    }

    public void set(T value) {
        Atomic.setRelease(this, VALUE, value);
    }

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

    public void ensureNotSet(String exceptionMessage) {
        if (!set()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public T ensureSet(String exceptionMessage) {
        if (unset()) {
            return get();
        }
        throw new IllegalStateException(exceptionMessage);
    }
}
