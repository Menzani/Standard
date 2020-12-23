package eu.menzani.system;

import eu.menzani.Native;

public class WindowsSleep {
    static {
        Native.init();
        if (!Platform.current().isWindows()) {
            throw new PlatformNotSupportedException();
        }
        prepare();
    }

    private static native void prepare();

    public static native void sleepHalfAMillisecond();
}
