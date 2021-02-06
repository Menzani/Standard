package eu.menzani.concurrent;

import eu.menzani.atomic.Atomic;
import eu.menzani.lang.Lang;

import java.util.function.Function;

public class LazyWithConstant<T, U> {
    private static final long VALUE = Lang.objectFieldOffset(LazyWithConstant.class, "value");

    private final Function<U, ? extends T> initializer;

    private T value;

    public LazyWithConstant(Function<U, ? extends T> initializer) {
        this.initializer = initializer;
    }

    public T get(U constant) {
        T value = Atomic.getAcquire(this, VALUE);
        if (value == null) {
            synchronized (this) {
                value = this.value;
                if (value == null) {
                    value = initializer.apply(constant);
                    Atomic.setRelease(this, VALUE, value);
                }
            }
        }
        return value;
    }
}
