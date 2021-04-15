package eu.menzani.concurrent;

import eu.menzani.lang.Assert;
import jdk.internal.misc.TerminatingThreadLocal;

public abstract class CleanedThreadLocal<T> extends java.lang.ThreadLocal<T> {
    private static final Object flag = new Object();

    private final Handle handle = new Handle();

    protected CleanedThreadLocal() {
    }

    @Override
    public void set(T value) {
        super.set(value);
        handle.onSet();
        onSet(value);
    }

    @Override
    public void remove() {
        super.remove();
        handle.remove();
        onRemove();
    }

    @Override
    protected T initialValue() {
        handle.onSet();
        return newInitialValue();
    }

    protected void onSet(T value) {
    }

    protected void onRemove() {
    }

    protected abstract T newInitialValue();

    protected void onCleaned(T value) {
    }

    private class Handle extends TerminatingThreadLocal<Object> {
        Handle() {
        }

        @Override
        protected void threadTerminated(Object value) {
            if (value == null) {
                return;
            }
            Assert.same(value, flag);

            onCleaned(CleanedThreadLocal.this.get());
            CleanedThreadLocal.super.remove();
        }

        void onSet() {
            set(flag);
        }
    }
}
