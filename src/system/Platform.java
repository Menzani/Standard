package eu.menzani.system;

import eu.menzani.SunUnsafe;
import eu.menzani.io.IOStreams;
import eu.menzani.lang.Nonblocking;
import eu.menzani.misc.ParseException;
import eu.menzani.struct.Patterns;

import java.io.IOException;
import java.util.List;

public enum Platform {
    LINUX_32(Family.LINUX, Architecture.BIT_32, false, true, false, true),
    LINUX_64(Family.LINUX, Architecture.BIT_64, false, true, false, false),
    WINDOWS_32(Family.WINDOWS, Architecture.BIT_32, true, false, false, true),
    WINDOWS_64(Family.WINDOWS, Architecture.BIT_64, true, false, false, false),
    MAC_32(Family.MAC, Architecture.BIT_32, false, false, true, true),
    MAC_64(Family.MAC, Architecture.BIT_64, false, false, true, false);

    private final Family family;
    private final Architecture architecture;
    private final boolean isWindows, isLinux, isMac;
    private final boolean is32Bit;

    Platform(Family family, Architecture architecture, boolean isWindows, boolean isLinux, boolean isMac, boolean is32Bit) {
        this.family = family;
        this.architecture = architecture;
        this.isWindows = isWindows;
        this.isLinux = isLinux;
        this.isMac = isMac;
        this.is32Bit = is32Bit;
    }

    public Family getFamily() {
        return family;
    }

    public Architecture getArchitecture() {
        return architecture;
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

    public static int getNumberOfCores() {
        return CPUInfo.numberOfCores;
    }

    public static int getNumberOfHardwareThreads() {
        return CPUInfo.numberOfHardwareThreads;
    }

    public static int getNumberOfHardwareThreadsPerCore() {
        return CPUInfo.numberOfHardwareThreadsPerCore;
    }

    static {
        String osName = new SystemProperty("os", "name").get();
        boolean is32Bit = new SystemProperty("sun", "arch", "data", "model").get().equals("32");
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

    public enum Family {
        WINDOWS,
        LINUX,
        MAC
    }

    public enum Architecture {
        BIT_32,
        BIT_64
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

    private static class CPUInfo extends PlatformFamilyDependant {
        static final int numberOfCores;
        static final int numberOfHardwareThreads;
        static final int numberOfHardwareThreadsPerCore;

        static {
            CPUInfo cpuInfo = new CPUInfo();
            numberOfCores = cpuInfo._numberOfCores;
            numberOfHardwareThreads = cpuInfo._numberOfHardwareThreads;
            numberOfHardwareThreadsPerCore = numberOfHardwareThreads / numberOfCores;
        }

        private int _numberOfCores;
        private int _numberOfHardwareThreads;

        @Override
        protected void init() {
            _numberOfCores = -1;
            _numberOfHardwareThreads = -1;
        }

        @Override
        protected void onWindows() throws IOException {
            ProcessBuilder builder = new ProcessBuilder(List.of(
                    "wmic", "CPU", "Get", "NumberOfCores,NumberOfLogicalProcessors", "/Format:List"));
            Process process = builder.start();
            Nonblocking.waitFor(process);
            String output = IOStreams.toString(process.getInputStream());
            String[] lines = Patterns.SPLIT_BY_NEWLINE.split(output);
            for (String line : lines) {
                if (line.isEmpty()) continue;
                int indexOfEquals = line.indexOf('=');
                String key = line.substring(0, indexOfEquals);
                int value = Integer.parseInt(line.substring(indexOfEquals + 1));
                switch (key) {
                    case "NumberOfCores":
                        _numberOfCores = value;
                        break;
                    case "NumberOfLogicalProcessors":
                        _numberOfHardwareThreads = value;
                        break;
                    default:
                        throw new ParseException();
                }
            }
            if (_numberOfCores == -1) throw new ParseException();
            if (_numberOfHardwareThreads == -1) throw new ParseException();
        }
    }
}
