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
    public SystemProperty set(String value) {
        super.set(value);
        return this;
    }

    @Override
    public SystemProperty setOrRemove(String value) {
        super.setOrRemove(value);
        return this;
    }

    @Override
    public SystemProperty setAsBoolean(boolean value) {
        super.setAsBoolean(value);
        return this;
    }

    @Override
    public SystemProperty remove() {
        super.remove();
        return this;
    }
}
