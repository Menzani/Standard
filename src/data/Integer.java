package eu.menzani.data;

public class Integer extends Element {
    private long value;
    private NumberSize size;

    public Integer() {
        set(0);
    }

    public Integer(short value) {
        set(value);
    }

    public Integer(int value) {
        set(value);
    }

    public Integer(long value) {
        set(value);
    }

    public short asShort() {
        if (size.contains(NumberSize.SHORT)) {
            return (short) value;
        }
        throw new ArithmeticException();
    }

    public int asInt() {
        if (size.contains(NumberSize.INT)) {
            return (int) value;
        }
        throw new ArithmeticException();
    }

    public long asLong() {
        return value;
    }

    public void set(short value) {
        this.value = value;
        size = NumberSize.SHORT;
    }

    public void set(int value) {
        this.value = value;
        size = NumberSize.INT;
    }

    public void set(long value) {
        this.value = value;
        size = NumberSize.LONG;
    }

    @Override
    public void reconstruct() {
        set(0);
    }

    @Override
    public void gc() {
    }
}
