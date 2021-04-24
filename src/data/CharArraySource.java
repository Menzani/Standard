package eu.menzani.data;

public class CharArraySource extends Source {
    private final char[] array;
    private final int to;

    private int position;

    public CharArraySource(char[] array) {
        this(array, 0, array.length);
    }

    public CharArraySource(char[] array, int from, int to) {
        this.array = array;
        position = from;
        this.to = to;
    }

    @Override
    public boolean hasNext() {
        return position != to;
    }

    @Override
    public char next() {
        return array[position++];
    }

    @Override
    public char peek() {
        return array[position];
    }

    @Override
    public void advance() {
        position++;
    }

    @Override
    public int position() {
        return position;
    }

    @Override
    public char charAt(int index) {
        return array[index];
    }
}
