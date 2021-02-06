package eu.menzani.system;

public class ApplicationProperty extends RuntimeProperty {
    public ApplicationProperty(Class<?> clazz, String first, String... more) {
        this(clazz.getPackageName(), first, more);
    }

    private ApplicationProperty(String initialValue, String first, String[] more) {
        super(initialValue, first, more);
    }

    public ApplicationProperty resolve(String first, String... more) {
        return new ApplicationProperty(key, first, more);
    }

    @Override
    public ApplicationProperty set(String value) {
        super.set(value);
        return this;
    }

    @Override
    public ApplicationProperty setOrRemove(String value) {
        super.setOrRemove(value);
        return this;
    }

    @Override
    public ApplicationProperty setAsBoolean(boolean value) {
        super.setAsBoolean(value);
        return this;
    }

    @Override
    public ApplicationProperty remove() {
        super.remove();
        return this;
    }
}
