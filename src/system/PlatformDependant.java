package eu.menzani.system;

import eu.menzani.lang.UncaughtException;

public abstract class PlatformDependant {
    protected PlatformDependant() {
        try {
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
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }

    protected void onWindows32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onWindows64() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux64() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onMac32() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onMac64() throws Exception {
        throw new PlatformNotSupportedException();
    }
}
