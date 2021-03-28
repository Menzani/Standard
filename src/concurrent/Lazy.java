package eu.menzani.concurrent;

import eu.menzani.atomic.Atomic;
import eu.menzani.lang.Lang;
import eu.menzani.object.ObjectFactory;

public class Lazy<T> {
    private static final long VALUE = Lang.objectFieldOffset(Lazy.class, "value");

    private final ObjectFactory<T> initializer;

    private T value;

    public static <T> Lazy<T> of(ObjectFactory<T> initializer) {
        return new Lazy<>(initializer);
    }

    private Lazy(ObjectFactory<T> initializer) {
        this.initializer = initializer;
    }

    public T get() {
        T value = Atomic.getAcquire(this, VALUE);
        if (value == null) {
            synchronized (this) {
                value = this.value;
                if (value == null) {
                    value = initializer.newInstance();
                    Atomic.setRelease(this, VALUE, value);
                }
            }
        }
        return value;
    }
}
