package eu.menzani.data;

public abstract class Source implements CharSequence {
    public abstract boolean hasNext();

    public abstract char next();

    public boolean nextIs(char character) {
        return next() == character;
    }

    public boolean nextIsNot(char character) {
        return next() != character;
    }

    public abstract char peek();

    public boolean peekIs(char character) {
        return peek() == character;
    }

    public abstract void advance();

    public abstract int position();

    @Override
    public int length() {
        return java.lang.Integer.MAX_VALUE;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        throw new UnsupportedOperationException();
    }
}
