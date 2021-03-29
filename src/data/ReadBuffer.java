package eu.menzani.data;

public class ReadBuffer implements CharSequence {
    private final char[] buffer;
    private final Source source;

    private int length;
    private int position;

    public ReadBuffer(int size, Source source) {
        buffer = new char[size];
        this.source = source;
    }

    public int position() {
        return position;
    }

    @Override
    public char charAt(int index) {
        return buffer[index];
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
        if (index == length) {
            length = source.fill(buffer);
            if (length == -1) {
                throw new ParseException();
            }
            position = 1;
            return buffer[0];
        }
        return buffer[index];
    }

    public boolean hasNext() {
        if (position == length) {
            length = source.fill(buffer);
            if (length == -1) {
                return false;
            }
            position = 0;
        }
        return true;
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        throw new UnsupportedOperationException();
    }
}
