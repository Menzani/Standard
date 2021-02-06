package eu.menzani.system;

import eu.menzani.Native;
import eu.menzani.lang.Assume;

public class Threads {
    static {
        Native.init();
    }

    public static void bindCurrentThreadToCPU(int cpu) {
        validateCPU(cpu);
        int errorCode = bindCurrentThread(cpu);
        if (errorCode != 0) {
            throw new ThreadManipulationException(errorCode);
        }
    }

    static void validateCPU(int cpu) {
        Assume.notNegative(cpu);
    }

    private static native int bindCurrentThread(int cpu);

    /**
     * On Linux, if not running under root, you need to add this to {@code /etc/security/limits.conf}:
     *
     * <pre>{@code
     * <user> hard rtprio 99
     * <user> soft rtprio 99
     * }</pre>
     */
    public static void setCurrentThreadPriorityToRealtime() {
        int errorCode = setCurrentThreadPriority();
        if (errorCode != 0) {
            throw new ThreadManipulationException(errorCode);
        }
    }

    private static native int setCurrentThreadPriority();

    public static ThreadSpreader.Builder spreadOverCPUs() {
        return new ThreadSpreader.Builder();
    }
}
