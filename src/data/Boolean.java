package eu.menzani.data;

import eu.menzani.lang.Immutable;

@Immutable
public final class Boolean extends Element {
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);

    private final boolean value;

    public static Boolean allocate(boolean value) {
        return value ? TRUE : FALSE;
    }

    private Boolean(boolean value) {
        this.value = value;
    }

    public boolean asPrimitive() {
        return value;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Boolean that = (Boolean) object;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return java.lang.Boolean.hashCode(value);
    }

    @Override
    public java.lang.String toString() {
        return java.lang.Boolean.toString(value);
    }

    @Override
    public void gc() {
    }

    @Override
    public void deallocate() {
    }
}
