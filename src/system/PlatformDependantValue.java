package eu.menzani.system;

public abstract class PlatformDependantValue<T> {
    public T get() {
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
    }

    protected T onWindows32() {
        throw new PlatformNotSupportedException();
    }

    protected T onWindows64() {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux32() {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux64() {
        throw new PlatformNotSupportedException();
    }

    protected T onMac32() {
        throw new PlatformNotSupportedException();
    }

    protected T onMac64() {
        throw new PlatformNotSupportedException();
    }
}
