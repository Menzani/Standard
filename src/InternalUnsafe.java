package eu.menzani;

import eu.menzani.lang.Invokable;
import eu.menzani.lang.Lang;
import eu.menzani.lang.Method;

import java.lang.reflect.AccessibleObject;

public class InternalUnsafe {
    public static final jdk.internal.misc.Unsafe UNSAFE;

    private static final long OVERRIDE;
    private static final Method<?> implAddOpens;
    private static final Method<?> implAddExports;

    public static void init() {
    }

    static {
        OVERRIDE = SunUnsafe.objectFieldOffset(AccessibleObject_fields.class, "override");

        final Class<?> moduleClass = Module.class;
        final Class<?> stringClass = String.class;
        implAddOpens = Invokable.ofMethod(moduleClass, "implAddOpens", stringClass, moduleClass);
        implAddOpens.forceAccessible();
        implAddExports = Invokable.ofMethod(moduleClass, "implAddExports", stringClass, moduleClass);
        implAddExports.forceAccessible();

        addOpens(Lang.JAVA_BASE_MODULE, Lang.EU_MENZANI_MODULE, "jdk.internal.misc");
        UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();
    }

    public static void forceAccessible(AccessibleObject accessibleObject) {
        SunUnsafe.UNSAFE.putBoolean(accessibleObject, OVERRIDE, true);
    }

    public static void addOpens(Module from, Module to, String packageName) {
        if (!from.isOpen(packageName, to)) {
            synchronized (implAddOpens) {
                implAddOpens.setTargetInstance(from);
                implAddOpens.call(packageName, to);
            }
        }
    }

    public static void addExports(Module from, Module to, String packageName) {
        if (!from.isExported(packageName, to)) {
            synchronized (implAddExports) {
                implAddExports.setTargetInstance(from);
                implAddExports.call(packageName, to);
            }
        }
    }

    private static class AccessibleObject_fields {
        boolean override;
        volatile Object accessCheckCache;
    }
}
