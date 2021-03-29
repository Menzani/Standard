package eu.menzani.data;

public class CharSequenceSource implements Source {
    private final CharSequence charSequence;
    private final int to;

    private int position;

    public CharSequenceSource(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public CharSequenceSource(CharSequence charSequence, int from, int to) {
        this.charSequence = charSequence;
        position = from;
        this.to = to;
    }

    @Override
    public int fill(char[] buffer) {
        int position = this.position;
        if (position == to) {
            return -1;
        }
        int batchSize = Math.min(to - position, buffer.length);
        for (int i = 0; i < batchSize; i++) {
            buffer[i] = charSequence.charAt(position + i);
        }
        this.position += batchSize;
        return batchSize;
    }
}
