package eu.menzani.struct;

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
