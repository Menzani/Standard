package eu.menzani;

import eu.menzani.lang.Lang;
import eu.menzani.lang.UncaughtException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class SunUnsafe {
    public static final Unsafe UNSAFE;

    static {
        Field field = Lang.getField(Unsafe.class, "theUnsafe");
        field.setAccessible(true);
        try {
            UNSAFE = (Unsafe) field.get(null);
        } catch (IllegalAccessException e) {
            throw new UncaughtException(e);
        }
    }

    public static long objectFieldOffset(Class<?> clazz, String fieldName) {
        return UNSAFE.objectFieldOffset(Lang.getField(clazz, fieldName));
    }
}
