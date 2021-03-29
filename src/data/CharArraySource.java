package eu.menzani.data;

public class CharArraySource implements Source {
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
    public int fill(char[] buffer) {
        if (position == to) {
            return -1;
        }
        int batchSize = Math.min(to - position, buffer.length);
        System.arraycopy(array, position, buffer, 0, batchSize);
        position += batchSize;
        return batchSize;
    }
}
