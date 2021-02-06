package eu.menzani.lang;

import eu.menzani.InternalUnsafe;

public class Constructor<T> implements Invokable<T> {
    private final java.lang.reflect.Constructor<T> constructor;

    Constructor(java.lang.reflect.Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public java.lang.reflect.Constructor<T> getExecutable() {
        return constructor;
    }

    @Override
    public void ensureAccessible() {
        if (!constructor.canAccess(null)) {
            constructor.setAccessible(true);
        }
    }

    @Override
    public void forceAccessible() {
        InternalUnsafe.setAccessible(constructor);
    }

    @Override
    public T call(Object... arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (ReflectiveOperationException e) {
            throw new UncaughtException(e);
        }
    }
}
