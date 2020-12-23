/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
