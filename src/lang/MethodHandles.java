package eu.menzani.lang;

import eu.menzani.system.IllegalAccessChecks;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class MethodHandles {
    public static final java.lang.invoke.MethodHandles.Lookup PUBLIC_LOOKUP = java.lang.invoke.MethodHandles.publicLookup();
    public static final MethodType VOID_VOID_METHOD_TYPE = MethodType.methodType(void.class);

    private static final java.lang.invoke.MethodHandles.Lookup lookup = java.lang.invoke.MethodHandles.lookup();

    public static MethodHandle ofVirtualPrivate(Class<?> clazz, String name, MethodType methodType) {
        IllegalAccessChecks.turnOff();
        try {
            return java.lang.invoke.MethodHandles.privateLookupIn(clazz, lookup).findVirtual(clazz, name, methodType);
        } catch (ReflectiveOperationException e) {
            throw new UncaughtException(e);
        }
    }
}
