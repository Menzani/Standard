package eu.menzani.data;

public class CharSequenceSource extends Source {
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
    public boolean hasNext() {
        return position != to;
    }

    @Override
    public char next() {
        return charSequence.charAt(position++);
    }

    @Override
    public char peek() {
        return charSequence.charAt(position);
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
        return charSequence.charAt(index);
    }
}
