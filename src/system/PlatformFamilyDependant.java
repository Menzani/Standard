package eu.menzani.system;

public abstract class PlatformFamilyDependant {
    protected PlatformFamilyDependant() {
        init();
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
        complete();
    }

    protected void init() {
    }

    protected void onWindows() {
        throw new PlatformNotSupportedException();
    }

    protected void onLinux() {
        throw new PlatformNotSupportedException();
    }

    protected void onMac() {
        throw new PlatformNotSupportedException();
    }

    protected void complete() {
    }
}
