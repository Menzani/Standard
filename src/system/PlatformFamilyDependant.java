package eu.menzani.system;

import eu.menzani.lang.UncaughtException;

public abstract class PlatformFamilyDependant {
    protected PlatformFamilyDependant() {
        try {
            switch (Platform.current().getFamily()) {
                case WINDOWS:
                    onWindows();
                    break;
                case LINUX:
                    onLinux();
                    break;
                case MAC:
                    onMac();
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }

    protected void onWindows() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux() throws Exception {
        throw new PlatformNotSupportedException();
    }

    protected void onMac() throws Exception {
        throw new PlatformNotSupportedException();
    }
}
