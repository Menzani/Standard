package eu.menzani.lang;

import eu.menzani.data.ParseException;

public class CharBuffer implements CharSequence {
    private final char[] buffer;
    private int length;
    private int position;

    public CharBuffer(int capacity) {
        buffer = new char[capacity];
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        return buffer[index];
    }

    public int position() {
        return position;
    }

    public char next() {
        return get(position++);
    }

    public char peek() {
        return get(position);
    }

    public boolean peekIs(char character) {
        return get(position) == character;
    }

    public void advance() {
        position++;
    }

    public boolean nextIs(char character) {
        return get(position++) == character;
    }

    public boolean nextIsNot(char character) {
        return get(position++) != character;
    }

    private char get(int index) {
        if (index < length) {
            return buffer[index];
        }
        throw new ParseException();
    }

    public boolean hasNext() {
        return position != length;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        throw new UnsupportedOperationException();
    }
}
