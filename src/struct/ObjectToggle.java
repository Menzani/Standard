package eu.menzani.struct;

public abstract class ObjectToggle<T> {
    public abstract boolean set();

    public abstract void set(T value);

    public abstract boolean unset();

    public abstract T get();

    public void ensureNotSet(String exceptionMessage) {
        if (!set()) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public T ensureSet(String exceptionMessage) {
        if (unset()) {
            return get();
        }
        throw new IllegalStateException(exceptionMessage);
    }
}
