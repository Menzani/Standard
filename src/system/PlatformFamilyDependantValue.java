package eu.menzani.system;

import eu.menzani.lang.UncaughtException;

public abstract class PlatformFamilyDependantValue<T> {
    public T get() {
        try {
            switch (Platform.current().getFamily()) {
                case WINDOWS:
                    return onWindows();
                case LINUX:
                    return onLinux();
                case MAC:
                    return onMac();
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }

    protected T onWindows() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected T onMac() throws Exception {
        throw new PlatformNotSupportedException();
    }
}
