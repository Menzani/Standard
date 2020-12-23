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

import eu.menzani.atomic.AtomicBoolean;
import eu.menzani.lang.Lang;

public class ConcurrentBooleanToggle extends BooleanToggle {
    private static final long VALUE = Lang.objectFieldOffset(ConcurrentBooleanToggle.class, "value");

    private boolean value;

    @Override
    public boolean toggleTrue() {
        return AtomicBoolean.compareAndSetVolatile(this, VALUE, false, true);
    }

    @Override
    public boolean toggleFalse() {
        return AtomicBoolean.compareAndSetVolatile(this, VALUE, true, false);
    }
}
