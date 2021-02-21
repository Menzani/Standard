package eu.menzani;

import eu.menzani.lang.Field;
import eu.menzani.lang.Lang;
import sun.misc.Unsafe;

public class SunUnsafe {
    public static final Unsafe UNSAFE;

    static {
        Field<Unsafe> field = Field.of(Unsafe.class, "theUnsafe");
        field.ensureAccessible();
        UNSAFE = field.getValue();
    }

    public static long objectFieldOffset(Class<?> clazz, String fieldName) {
        return UNSAFE.objectFieldOffset(Lang.getField(clazz, fieldName));
    }
}
