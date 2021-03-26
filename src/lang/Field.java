package eu.menzani.lang;

import eu.menzani.InternalUnsafe;

import java.lang.reflect.Modifier;

public class Field<T> {
    private final java.lang.reflect.Field field;
    private Object targetInstance;

    public static <T> Field<T> of(Object instance, String name) {
        Field<T> field = of(instance.getClass(), name);
        field.setTargetInstance(instance);
        return field;
    }

    public static <T> Field<T> of(Class<?> clazz, String name) {
        return new Field<>(Lang.getField(clazz, name));
    }

    public Field(java.lang.reflect.Field field) {
        this.field = field;
    }

    public java.lang.reflect.Field getJavaObject() {
        return field;
    }

    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    public void setAccessible() {
        field.setAccessible(true);
    }

    public void forceAccessible() {
        InternalUnsafe.forceAccessible(field);
    }

    public void setTargetInstance(Object targetInstance) {
        this.targetInstance = targetInstance;
    }

    public void setValue(T value) {
        try {
            field.set(targetInstance, value);
        } catch (IllegalAccessException e) {
            throw new UncaughtException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        try {
            return (T) field.get(targetInstance);
        } catch (IllegalAccessException e) {
            throw new UncaughtException(e);
        }
    }
}
