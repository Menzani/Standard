package eu.menzani.system;

public class PlatformTest {
    public void isWindows() {
        assert Platform.WINDOWS_32.isWindows();
        assert Platform.WINDOWS_64.isWindows();
    }

    public void isLinux() {
        assert Platform.LINUX_32.isLinux();
        assert Platform.LINUX_64.isLinux();
    }

    public void is32Bit() {
        assert Platform.WINDOWS_32.is32Bit();
        assert Platform.LINUX_32.is32Bit();
    }

    public void is64Bit() {
        assert Platform.WINDOWS_64.is64Bit();
        assert Platform.LINUX_64.is64Bit();
    }
}
