package eu.menzani.lang;

import java.lang.reflect.Executable;

public interface Invokable<T> {
    Executable getExecutable();

    void ensureAccessible();

    void forceAccessible();

    T call(Object... arguments);

    @SuppressWarnings("unchecked")
    static <T> Invokable<T> of(Executable executable) {
        if (executable instanceof java.lang.reflect.Constructor<?>) {
            return of((java.lang.reflect.Constructor<T>) executable);
        }
        return of((java.lang.reflect.Method) executable);
    }

    static <T> Method<T> of(java.lang.reflect.Method method) {
        return new Method<>(method);
    }

    static <T> Constructor<T> of(java.lang.reflect.Constructor<T> constructor) {
        return new Constructor<>(constructor);
    }

    static <T> Method<T> ofMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        return of(Lang.getMethod(clazz, name, parameterTypes));
    }

    static <T> Constructor<T> ofConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        return of(Lang.getConstructor(clazz, parameterTypes));
    }
}
