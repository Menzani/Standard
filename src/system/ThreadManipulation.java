package eu.menzani.system;

public class ThreadManipulation extends AbstractThreadManipulation {
    private static final ThreadManipulation doNothing = new ThreadManipulation(-1, false);
    private static final ThreadManipulation setPriorityToRealtime = new ThreadManipulation(-1, true);

    private final int cpuToBindTo;

    public static ThreadManipulation doNothing() {
        return doNothing;
    }

    public static ThreadManipulation setPriorityToRealtime() {
        return setPriorityToRealtime;
    }

    public static ThreadManipulation bindToCPU(int cpu) {
        Threads.validateCPU(cpu);
        return new ThreadManipulation(cpu, false);
    }

    public static ThreadManipulation bindFromSpreader(ThreadSpreader spreader) {
        return new ThreadManipulation(spreader.nextCPU(), false);
    }

    public static ThreadManipulation setPriorityToRealtimeAndBindToCPU(int cpu) {
        Threads.validateCPU(cpu);
        return new ThreadManipulation(cpu, true);
    }

    public static ThreadManipulation setPriorityToRealtimeAndBindFromSpreader(ThreadSpreader spreader) {
        return new ThreadManipulation(spreader.nextCPU(), true);
    }

    private ThreadManipulation(int cpuToBindTo, boolean shouldSetPriorityToRealtime) {
        super(shouldSetPriorityToRealtime);
        this.cpuToBindTo = cpuToBindTo;
    }

    public void applyToCurrentThread() {
        if (cpuToBindTo != -1) {
            Threads.bindCurrentThreadToCPU(cpuToBindTo);
        }
        applyToThread();
    }
}
