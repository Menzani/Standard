package eu.menzani.data;

public class CharSequenceDestination implements Destination {
    private final StringBuilder builder;

    public CharSequenceDestination() {
        this(512);
    }

    public CharSequenceDestination(int initialCapacity) {
        builder = new StringBuilder(initialCapacity);
    }

    @Override
    public void send(char[] buffer, int end) {
        builder.append(buffer, 0, end);
    }

    public StringBuilder getStringBuilder() {
        return builder;
    }

    public java.lang.String getString() {
        return builder.toString();
    }
}
