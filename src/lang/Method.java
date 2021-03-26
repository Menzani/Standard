package eu.menzani.lang;

import eu.menzani.InternalUnsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class Method<T> implements Invokable<T> {
    private final java.lang.reflect.Method method;
    private Object targetInstance;

    Method(java.lang.reflect.Method method) {
        this.method = method;
    }

    @Override
    public java.lang.reflect.Method getExecutable() {
        return method;
    }

    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override
    public void setAccessible() {
        method.setAccessible(true);
    }

    @Override
    public void forceAccessible() {
        InternalUnsafe.forceAccessible(method);
    }

    public void setTargetInstance(Object targetInstance) {
        this.targetInstance = targetInstance;
    }

    @Override
    public T call() {
        return call(Lang.NO_ARGS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T call(Object... arguments) {
        try {
            return (T) method.invoke(targetInstance, arguments);
        } catch (IllegalAccessException e) {
            throw new UncaughtException(e);
        } catch (InvocationTargetException e) {
            throw new UncaughtException(e.getCause());
        }
    }
}
