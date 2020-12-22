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

package eu.menzani.system;

public enum Version {
    JAVA_11, JAVA_12, JAVA_13, JAVA_14, JAVA_15, JAVA_16, JAVA_17, JAVA_18, JAVA_19;

    public boolean isNewerThan(Version other) {
        return compareTo(other) > 0;
    }

    public boolean isOlderThan(Version other) {
        return compareTo(other) < 0;
    }

    public boolean isNotOlderThan(Version other) {
        return compareTo(other) >= 0;
    }

    public boolean isNotNewerThan(Version other) {
        return compareTo(other) <= 0;
    }

    private static final Version current;

    public static Version current() {
        return current;
    }

    static {
        switch (System.getProperty("java.version").charAt(1)) {
            case '1':
                current = JAVA_11;
                break;
            case '2':
                current = JAVA_12;
                break;
            case '3':
                current = JAVA_13;
                break;
            case '4':
                current = JAVA_14;
                break;
            case '5':
                current = JAVA_15;
                break;
            case '6':
                current = JAVA_16;
                break;
            case '7':
                current = JAVA_17;
                break;
            case '8':
                current = JAVA_18;
                break;
            case '9':
                current = JAVA_19;
                break;
            default:
                throw new AssertionError("Version is unknown.");
        }
    }
}
