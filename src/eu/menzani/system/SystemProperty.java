package eu.menzani.system;

public class SystemProperty extends RuntimeProperty {
    public SystemProperty(String first, String... more) {
        this(null, first, more);
    }

    private SystemProperty(String initialValue, String first, String[] more) {
        super(initialValue, first, more);
    }

    public SystemProperty resolve(String first, String... more) {
        return new SystemProperty(key, first, more);
    }

    @Override
    public String toString() {
        return key;
    }
}
