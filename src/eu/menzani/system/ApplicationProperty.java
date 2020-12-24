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
}
