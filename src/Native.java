package eu.menzani;

import eu.menzani.misc.NativeLibrary;

public class Native {
    public static void init() {
    }

    static {
        NativeLibrary nativeLibrary = new NativeLibrary(2278256207267942924L, Native.class, "standard");
        nativeLibrary.load();
    }
}
