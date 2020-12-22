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

public abstract class ObjectToggle<T> {
    public abstract boolean toggleSet();

    public abstract void set(T value);

    public abstract boolean toggleNotSet();

    public abstract T get();

    public void ensureNotSet(String exceptionMessage) {
        if (!toggleSet()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public T ensureSet(String exceptionMessage) {
        if (!toggleNotSet()) {
            throw new IllegalStateException(exceptionMessage);
        }
        return get();
    }
}
