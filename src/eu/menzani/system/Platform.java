package eu.menzani.system;

import eu.menzani.InternalUnsafe;

public enum Platform {
    LINUX_32(false, true, false, true),
    LINUX_64(false, true, false, false),
    WINDOWS_32(true, false, false, true),
    WINDOWS_64(true, false, false, false),
    MAC_32(false, false, true, true),
    MAC_64(false, false, true, false);

    private final boolean isWindows, isLinux, isMac;
    private final boolean is32Bit;

    Platform(boolean isWindows, boolean isLinux, boolean isMac, boolean is32Bit) {
        this.isWindows = isWindows;
        this.isLinux = isLinux;
        this.isMac = isMac;
        this.is32Bit = is32Bit;
    }

    public boolean isWindows() {
        return isWindows;
    }

    public boolean isLinux() {
        return isLinux;
    }

    public boolean isMac() {
        return isMac;
    }

    public boolean is32Bit() {
        return is32Bit;
    }

    public boolean is64Bit() {
        return !is32Bit;
    }

    private static final Platform current;

    public static Platform current() {
        return current;
    }

    public static boolean areOopsCompressed() {
        return InternalUnsafe.OopsCompressed.value;
    }

    static {
        String osName = System.getProperty("os.name");
        boolean is32Bit = System.getProperty("sun.arch.data.model").equals("32");
        if (osName.contains("Windows")) {
            if (is32Bit) {
                current = WINDOWS_32;
            } else {
                current = WINDOWS_64;
            }
        } else if (osName.contains("Linux")) {
            if (is32Bit) {
                current = LINUX_32;
            } else {
                current = LINUX_64;
            }
        } else if (osName.contains("Mac")) {
            if (is32Bit) {
                current = MAC_32;
            } else {
                current = MAC_64;
            }
        } else {
            throw new AssertionError();
        }
    }
}
