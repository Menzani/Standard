package eu.menzani.data;

public class Decimal extends Element {
    private double value;
    private NumberSize size;

    public Decimal() {
        set(0F);
    }

    public Decimal(float value) {
        set(value);
    }

    public Decimal(double value) {
        set(value);
    }

    public float asFloat() {
        if (size.contains(NumberSize.FLOAT)) {
            return (float) value;
        }
        throw new ArithmeticException();
    }

    public double asDouble() {
        return value;
    }

    public void set(float value) {
        this.value = value;
        size = NumberSize.FLOAT;
    }

    public void set(double value) {
        this.value = value;
        size = NumberSize.DOUBLE;
    }

    @Override
    public void reconstruct() {
        set(0F);
    }

    @Override
    public void gc() {
    }
}
