package eu.menzani.system;

import eu.menzani.lang.UncaughtException;

public abstract class PlatformDependantValue<T> {
    public T get() {
        try {
            switch (Platform.current()) {
                case LINUX_32:
                    return onLinux32();
                case LINUX_64:
                    return onLinux64();
                case WINDOWS_32:
                    return onWindows32();
                case WINDOWS_64:
                    return onWindows64();
                case MAC_32:
                    return onMac32();
                case MAC_64:
                    return onMac64();
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }

    protected T onWindows32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onWindows64() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux64() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onMac32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onMac64() throws Exception {
        throw new PlatformNotSupportedException();
    }
}
