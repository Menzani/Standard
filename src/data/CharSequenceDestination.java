package eu.menzani.data;

import eu.menzani.lang.StringBuilders;

public class CharSequenceDestination extends Destination {
    private final StringBuilder builder;

    public CharSequenceDestination() {
        this(512);
    }

    public CharSequenceDestination(int initialCapacity) {
        builder = new StringBuilder(initialCapacity);
    }

    @Override
    public void append(char character) {
        builder.append(character);
    }

    @Override
    public void append(java.lang.String string) {
        int start = builder.length();
        builder.append(string);
        if (shouldReplace()) {
            StringBuilders.replace(builder, start, builder.length(), getTargetReplacements());
        }
    }

    @Override
    public void append(StringBuilder builder) {
        int start = this.builder.length();
        this.builder.append(builder);
        if (shouldReplace()) {
            StringBuilders.replace(this.builder, start, this.builder.length(), getTargetReplacements());
        }
    }

    @Override
    public void deleteLastChar() {
        builder.setLength(builder.length() - 1);
    }

    public StringBuilder getStringBuilder() {
        return builder;
    }

    public java.lang.String getString() {
        return builder.toString();
    }
}
