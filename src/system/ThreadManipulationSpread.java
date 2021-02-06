package eu.menzani.system;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Optional;

public class ThreadManipulationSpread extends AbstractThreadManipulation {
    private static final ThreadManipulationSpread doNothing = new ThreadManipulationSpread(null, false);
    private static final ThreadManipulationSpread setPriorityToRealtime = new ThreadManipulationSpread(null, true);

    private final ThreadSpreader spreader;

    public static ThreadManipulationSpread doNothing() {
        return doNothing;
    }

    public static ThreadManipulationSpread setPriorityToRealtime() {
        return setPriorityToRealtime;
    }

    public static ThreadManipulationSpread bind(ThreadSpreader spreader) {
        Assume.notNull(spreader);
        return new ThreadManipulationSpread(spreader, false);
    }

    public static ThreadManipulationSpread setPriorityToRealtimeAndBind(ThreadSpreader spreader) {
        Assume.notNull(spreader);
        return new ThreadManipulationSpread(spreader, true);
    }

    private ThreadManipulationSpread(@Optional ThreadSpreader spreader, boolean shouldSetPriorityToRealtime) {
        super(shouldSetPriorityToRealtime);
        this.spreader = spreader;
    }

    public void applyToNextThread() {
        if (spreader != null) {
            spreader.bindCurrentThreadToNextCPU();
        }
        applyToThread();
    }

    public void reset() {
        if (spreader != null) {
            spreader.reset();
        }
    }
}
