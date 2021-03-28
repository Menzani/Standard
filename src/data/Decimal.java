package eu.menzani.data;

import eu.menzani.object.Allocator;

public class Decimal extends Number {
    private static final Allocator<Decimal> allocator = Allocator.create(Decimal::new);

    double value;
    private NumberSize size;

    public static Decimal allocate() {
        return allocate(0F);
    }

    public static Decimal allocate(float value) {
        Decimal instance = allocator.allocate();
        instance.set(value);
        return instance;
    }

    public static Decimal allocate(double value) {
        Decimal instance = allocator.allocate();
        instance.set(value);
        return instance;
    }

    private Decimal() {
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
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;

        if (object instanceof Integer) {
            return ((Integer) object).value == value;
        }
        if (object instanceof Decimal) {
            return ((Decimal) object).value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public java.lang.String toString() {
        return Double.toString(value);
    }

    @Override
    public int compareTo(Number other) {
        if (other instanceof Integer) {
            return Double.compare(((Integer) other).value, value);
        }
        if (other instanceof Decimal) {
            return Double.compare(((Decimal) other).value, value);
        }
        throw new AssertionError();
    }

    @Override
    public void gc() {
    }

    @Override
    public void deallocate() {
        allocator.deallocate(this);
    }
}
