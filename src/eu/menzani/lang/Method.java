package eu.menzani.lang;

import eu.menzani.InternalUnsafe;

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
    public void ensureAccessible() {
        if (!method.canAccess(targetInstance)) {
            method.setAccessible(true);
        }
    }

    @Override
    public void forceAccessible() {
        InternalUnsafe.setAccessible(method);
    }

    public void setTargetInstance(Object targetInstance) {
        this.targetInstance = targetInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T call(Object... arguments) {
        try {
            return (T) method.invoke(targetInstance, arguments);
        } catch (ReflectiveOperationException e) {
            throw new UncaughtException(e);
        }
    }
}
