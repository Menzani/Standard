package eu.menzani.lang;

import eu.menzani.InternalUnsafe;
import eu.menzani.error.GlobalStackTraceFilter;

import java.lang.reflect.InvocationTargetException;

public class Constructor<T> implements Invokable<T> {
    static {
        GlobalStackTraceFilter.getInstance().addMethodToRemove(Constructor.class, "call");
    }

    private final java.lang.reflect.Constructor<T> constructor;

    Constructor(java.lang.reflect.Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public java.lang.reflect.Constructor<T> getExecutable() {
        return constructor;
    }

    @Override
    public void setAccessible() {
        constructor.setAccessible(true);
    }

    @Override
    public void forceAccessible() {
        InternalUnsafe.forceAccessible(constructor);
    }

    @Override
    @RemovedFromStackTrace
    public T call() {
        return call(Lang.NO_ARGS);
    }

    @Override
    @RemovedFromStackTrace
    public T call(Object... arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new UncaughtException(e);
        } catch (InvocationTargetException e) {
            throw new UncaughtException(e.getCause());
        }
    }
}
