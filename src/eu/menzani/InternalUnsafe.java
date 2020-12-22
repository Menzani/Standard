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

package eu.menzani;

import eu.menzani.lang.Invokable;
import eu.menzani.lang.Lang;
import eu.menzani.lang.Method;
import eu.menzani.system.Platform;
import eu.menzani.system.Version;

import java.lang.reflect.AccessibleObject;

public class InternalUnsafe {
    public static final jdk.internal.misc.Unsafe UNSAFE;

    private static final long OVERRIDE;
    private static final Method<?> implAddOpens;

    public static void init() {
    }

    static {
        if (Version.current() == Version.JAVA_11) {
            OVERRIDE = SunUnsafe.objectFieldOffset(AccessibleObject.class, "override");
        } else if (Platform.current().is32Bit()) {
            OVERRIDE = 8L;
        } else if (Platform.areOopsCompressed()) {
            OVERRIDE = 12L;
        } else {
            OVERRIDE = 16L;
        }

        final Class<?> clazz = Module.class;
        implAddOpens = Invokable.ofMethod(clazz, "implAddOpens", String.class, clazz);
        implAddOpens.forceAccessible();

        addOpens(Lang.JAVA_BASE_MODULE, Lang.EU_MENZANI_MODULE, "jdk.internal.misc");
        UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();
    }

    public static void setAccessible(AccessibleObject accessibleObject) {
        SunUnsafe.UNSAFE.putBoolean(accessibleObject, OVERRIDE, true);
    }

    public static void addOpens(Module from, Module to, String packageName) {
        if (!from.isOpen(packageName, to)) {
            implAddOpens.setTargetInstance(from);
            implAddOpens.call(packageName, to);
        }
    }

    public static class OopsCompressed {
        public static final boolean value;

        int i;

        static {
            long offset = SunUnsafe.objectFieldOffset(OopsCompressed.class, "i");
            if (offset == 8L) {
                assert Platform.current().is32Bit();
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
}
