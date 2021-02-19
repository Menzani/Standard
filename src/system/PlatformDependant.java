package eu.menzani.system;

public abstract class PlatformDependant {
    protected PlatformDependant() {
        init();
        switch (Platform.current()) {
            case LINUX_32:
                onLinux32();
                break;
            case LINUX_64:
                onLinux64();
                break;
            case WINDOWS_32:
                onWindows32();
                break;
            case WINDOWS_64:
                onWindows64();
                break;
            case MAC_32:
                onMac32();
                break;
            case MAC_64:
                onMac64();
                break;
            default:
                throw new AssertionError();
        }
    }

    protected void init() {
    }

    protected void onWindows32() {
        throw new PlatformNotSupportedException();
    }

    protected void onWindows64() {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux32() {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux64() {
        throw new PlatformNotSupportedException();
    }

    protected void onMac32() {
        throw new PlatformNotSupportedException();
    }

    protected void onMac64() {
        throw new PlatformNotSupportedException();
    }
}
