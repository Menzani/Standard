package eu.menzani.system;

import eu.menzani.SunUnsafe;

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
        return OopsCompressed.value;
    }

    static {
        String osName = new SystemProperty("os.name").get();
        boolean is32Bit = new SystemProperty("sun.arch.data.model").get().equals("32");
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

    private static class OopsCompressed {
        static final boolean value;

        private int i;

        static {
            long offset = SunUnsafe.objectFieldOffset(OopsCompressed.class, "i");
            if (offset == 8L) {
                assert current().is32Bit();
                value = false;
            } else if (offset == 12L) {
                value = true;
            } else if (offset == 16L) {
                value = false;
            } else {
                throw new AssertionError();
            }
        }
    }
}
