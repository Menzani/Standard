package eu.menzani.lang;

import eu.menzani.InternalUnsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Lang {
    public static final java.lang.Module JAVA_BASE_MODULE = IllegalArgumentException.class.getModule();
    public static final java.lang.Module EU_MENZANI_MODULE = Lang.class.getModule();

    static final Object[] NO_ARGS = new Object[0];
    private static final Class<?>[] noParameterTypes = new Class<?>[0];

    public static Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new UncaughtException(e);
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new UncaughtException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new UncaughtException(e);
        }
    }

    public static long objectFieldOffset(Class<?> clazz, String fieldName) {
        return InternalUnsafe.UNSAFE.objectFieldOffset(clazz, fieldName);
    }

    public static StaticFieldOffset staticFieldOffset(Class<?> clazz, String fieldName) {
        Field field = getField(clazz, fieldName);
        return new StaticFieldOffset(InternalUnsafe.UNSAFE.staticFieldBase(field), InternalUnsafe.UNSAFE.staticFieldOffset(field));
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor(noParameterTypes).newInstance(NO_ARGS);
        } catch (ReflectiveOperationException e) {
            throw new UncaughtException(e);
        }
    }
}
