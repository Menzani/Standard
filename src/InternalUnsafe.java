package eu.menzani;

import eu.menzani.lang.Invokable;
import eu.menzani.lang.Lang;
import eu.menzani.lang.Method;
import eu.menzani.system.Platform;
import eu.menzani.system.Version;

import java.lang.reflect.AccessibleObject;

public class InternalUnsafe {
    public static final jdk.internal.misc.Unsafe UNSAFE;

    private static final long OVERRIDE;
    private static final Method<?> implAddOpens;

    public static void init() {
    }

    static {
        if (Version.current() == Version.JAVA_11) {
            OVERRIDE = SunUnsafe.objectFieldOffset(AccessibleObject.class, "override");
        } else if (Platform.current().is32Bit()) {
            OVERRIDE = 8L;
        } else if (Platform.areOopsCompressed()) {
            OVERRIDE = 12L;
        } else {
            OVERRIDE = 16L;
        }

        final Class<?> clazz = Module.class;
        implAddOpens = Invokable.ofMethod(clazz, "implAddOpens", String.class, clazz);
        implAddOpens.forceAccessible();

        addOpens(Lang.JAVA_BASE_MODULE, Lang.EU_MENZANI_MODULE, "jdk.internal.misc");
        UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();
    }

    public static void setAccessible(AccessibleObject accessibleObject) {
        SunUnsafe.UNSAFE.putBoolean(accessibleObject, OVERRIDE, true);
    }

    public static void addOpens(Module from, Module to, String packageName) {
        if (!from.isOpen(packageName, to)) {
            implAddOpens.setTargetInstance(from);
            implAddOpens.call(packageName, to);
        }
    }
}
