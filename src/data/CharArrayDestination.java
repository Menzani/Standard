package eu.menzani.data;

import java.util.Arrays;

public class CharArrayDestination implements Destination {
    private char[] array;
    private int position;

    public CharArrayDestination() {
        this(512);
    }

    public CharArrayDestination(int initialLength) {
        array = new char[initialLength];
    }

    @Override
    public void send(char[] buffer, int end) {
        int newPosition = position + end;
        int length = array.length;
        if (newPosition > length) {
            array = Arrays.copyOf(array, length * 2);
        }
        System.arraycopy(buffer, 0, array, position, end);
        position = newPosition;
    }

    public char[] getArray() {
        return array;
    }

    public int getLength() {
        return position;
    }

    public char[] getSizedArray() {
        return Arrays.copyOf(array, position);
    }
}
