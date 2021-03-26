package eu.menzani.data;

import eu.menzani.lang.Immutable;

@Immutable
public final class Boolean extends Element {
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);

    private final boolean value;

    private Boolean(boolean value) {
        this.value = value;
    }

    public boolean asPrimitive() {
        return value;
    }

    @Override
    public void reconstruct() {
    }

    @Override
    public void gc() {
    }
}
