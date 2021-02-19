package eu.menzani.system;

public abstract class PlatformFamilyDependantValue<T> {
    public T get() {
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
    }

    protected T onWindows() {
        throw new PlatformNotSupportedException();
    }

    protected T onLinux() {
        throw new PlatformNotSupportedException();
    }

    protected T onMac() {
        throw new PlatformNotSupportedException();
    }
}
