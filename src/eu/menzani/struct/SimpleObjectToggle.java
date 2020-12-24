package eu.menzani.struct;

public class SimpleObjectToggle<T> extends ObjectToggle<T> {
    private T value;

    @Override
    public boolean toggleSet() {
        return value == null;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean toggleNotSet() {
        return value != null;
    }

    @Override
    public T get() {
        T value = this.value;
        this.value = null;
        return value;
    }
}
