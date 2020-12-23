package eu.menzani.concurrent;

import eu.menzani.atomic.AtomicBoolean;
import eu.menzani.lang.Lang;

/**
 * Coordinator thread:
 *
 * <pre>{@code
 * for (WorkerThread thread : threads) {
 *     thread.synchronizer.waitUntilReady();
 * }
 * for (WorkerThread thread : threads) {
 *     thread.synchronizer.commenceExecution();
 * }
 * }</pre>
 * <p>
 * Worker threads:
 *
 * <pre>{@code
 * // Do startup logic
 * synchronizer.synchronize(); // Start all together
 * }</pre>
 */
public class ThreadSynchronizer {
    private static final long NOT_READY, DO_NOT_COMMENCE;

    static {
        final Class<?> clazz = ThreadSynchronizer.class;
        NOT_READY = Lang.objectFieldOffset(clazz, "notReady");
        DO_NOT_COMMENCE = Lang.objectFieldOffset(clazz, "doNotCommence");
    }

    private boolean notReady;
    private boolean doNotCommence;

    {
        reset();
    }

    public void waitUntilReady() {
        while (AtomicBoolean.getOpaque(this, NOT_READY)) {
            Thread.onSpinWait();
        }
    }

    public void commenceExecution() {
        AtomicBoolean.setOpaque(this, DO_NOT_COMMENCE, false);
    }

    public void synchronize() {
        AtomicBoolean.setOpaque(this, NOT_READY, false);
        while (AtomicBoolean.getOpaque(this, DO_NOT_COMMENCE)) {
            Thread.onSpinWait();
        }
    }

    public void reset() {
        AtomicBoolean.setOpaque(this, NOT_READY, true);
        AtomicBoolean.setOpaque(this, DO_NOT_COMMENCE, true);
    }
}
