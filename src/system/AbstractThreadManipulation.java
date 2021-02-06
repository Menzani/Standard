package eu.menzani.system;

abstract class AbstractThreadManipulation {
    private final boolean shouldSetPriorityToRealtime;

    AbstractThreadManipulation(boolean shouldSetPriorityToRealtime) {
        this.shouldSetPriorityToRealtime = shouldSetPriorityToRealtime;
    }

    void applyToThread() {
        if (shouldSetPriorityToRealtime) {
            Threads.setCurrentThreadPriorityToRealtime();
        }
    }
}
