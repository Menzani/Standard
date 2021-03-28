package eu.menzani.system;

import eu.menzani.InternalUnsafe;
import eu.menzani.lang.Lang;
import jdk.internal.module.IllegalAccessLogger;

public class IllegalAccessChecks {
    static {
        InternalUnsafe.addExports(Lang.JAVA_BASE_MODULE, Lang.EU_MENZANI_MODULE, "jdk.internal.module");

        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(IllegalAccessLogger.Mode.ONESHOT, System.err);
        builder.complete();
    }

    public static void turnOff() {
    }
}
