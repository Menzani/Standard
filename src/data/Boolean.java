package eu.menzani.data;

import eu.menzani.lang.Immutable;

@Immutable
public final class Boolean extends Element {
    public static final Boolean TRUE = new Boolean(true, "true");
    public static final Boolean FALSE = new Boolean(false, "false");

    private final boolean value;
    private final java.lang.String asString;

    public static Boolean allocate(boolean value) {
        return value ? TRUE : FALSE;
    }

    private Boolean(boolean value, java.lang.String asString) {
        this.value = value;
        this.asString = asString;
    }

    public boolean asPrimitive() {
        return value;
    }

    public java.lang.String asString() {
        return asString;
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
        return asString;
    }

    @Override
    public void gc() {
    }

    @Override
    public void deallocate() {
    }
}
