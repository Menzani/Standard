package eu.menzani.data;

import eu.menzani.lang.BuildableCharArray;

public class WriteBuffer extends BuildableCharArray {
    private final int size;
    private final Destination destination;

    public WriteBuffer(int size, Destination destination) {
        super(size * 2);
        this.size = size;
        this.destination = destination;
    }

    public void checkFull() {
        if (builder.length() > size) {
            flush();
        }
    }

    @Override
    protected void flush(char[] buffer, int end) {
        destination.send(buffer, end);
    }
}
