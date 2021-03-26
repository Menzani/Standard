package eu.menzani.data;

import eu.menzani.lang.View;

public class String extends Element {
    private StringBuilder value;

    public String() {
        value = new StringBuilder();
    }

    public String(java.lang.String value) {
        this.value = new StringBuilder(value);
    }

    public String(CharSequence value) {
        this.value = new StringBuilder(value);
    }

    public java.lang.String asJavaString() {
        return value.toString();
    }

    @View
    public CharSequence asCharSequence() {
        return value;
    }

    public StringBuilder set() {
        return value;
    }

    @Override
    public void reconstruct() {
        value.setLength(0);
    }

    @Override
    public void gc() {
        value = new StringBuilder();
    }
}
