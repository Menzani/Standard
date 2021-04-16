package eu.menzani.concurrent;

import eu.menzani.lang.Assert;
import eu.menzani.object.ObjectFactory;
import jdk.internal.misc.TerminatingThreadLocal;

import java.util.function.Consumer;

public class CleanedThreadLocal<T> extends java.lang.ThreadLocal<T> {
    private static final Object flag = new Object();

    private final Handle handle = new Handle();
    private final ObjectFactory<T> factory;
    private final Consumer<? super T> onClean;

    public CleanedThreadLocal(ObjectFactory<T> factory) {
        this(factory, null);
    }

    public CleanedThreadLocal(ObjectFactory<T> factory, Consumer<? super T> onClean) {
        this.factory = factory;
        this.onClean = onClean;
    }

    @Override
    public void set(T value) {
        super.set(value);
        handle.onSet();
    }

    @Override
    public void remove() {
        super.remove();
        handle.remove();
    }

    @Override
    protected T initialValue() {
        handle.onSet();
        return factory.newInstance();
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

            if (onClean != null) {
                onClean.accept(CleanedThreadLocal.this.get());
            }
            CleanedThreadLocal.super.remove();
        }

        void onSet() {
            set(flag);
        }
    }
}
