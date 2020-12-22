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

package eu.menzani.util;

import eu.menzani.atomic.Atomic;
import eu.menzani.lang.Lang;

public class ConcurrentObjectToggle<T> extends ObjectToggle<T> {
    private static final long VALUE, LAST_SET_VALUE;

    static {
        final Class<?> clazz = ConcurrentObjectToggle.class;
        VALUE = Lang.objectFieldOffset(clazz, "value");
        LAST_SET_VALUE = Lang.objectFieldOffset(clazz, "lastSetValue");
    }

    private static final Object placeholder = new Object();

    private Object value;
    private T lastSetValue;

    @Override
    public boolean toggleSet() {
        return Atomic.compareAndSetVolatile(this, VALUE, null, placeholder);
    }

    @Override
    public void set(T value) {
        Atomic.setRelease(this, VALUE, value);
    }

    @Override
    public boolean toggleNotSet() {
        Object value = Atomic.getAndSetVolatile(this, VALUE, null);
        if (value == null) {
            return false;
        }
        if (value == placeholder) {
            do {
                value = Atomic.getAndSetVolatile(this, VALUE, null);
            } while (value == null);
        }
        Atomic.setRelease(this, LAST_SET_VALUE, value);
        return true;
    }

    @Override
    public T get() {
        return Atomic.getAcquire(this, LAST_SET_VALUE);
    }
}
