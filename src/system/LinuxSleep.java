package eu.menzani.system;

import eu.menzani.Native;

public class LinuxSleep {
    static {
        Native.init();
        if (!Platform.current().isLinux()) {
            throw new PlatformNotSupportedException();
        }
    }

    public static native void sleep(int nanoseconds);
}
