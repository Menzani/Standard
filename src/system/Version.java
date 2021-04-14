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
        switch (new SystemProperty("java", "version").get().charAt(1)) {
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
                throw new PlatformNotSupportedException();
        }
    }
}
