package eu.menzani.data;

import eu.menzani.object.Allocator;

public class Integer extends Number {
    private static final Allocator<Integer> allocator = Allocator.create(Integer::new);

    long value;
    private NumberSize size;

    public static Integer allocate() {
        return allocate(0);
    }

    public static Integer allocate(short value) {
        Integer instance = allocator.allocate();
        instance.set(value);
        return instance;
    }

    public static Integer allocate(int value) {
        Integer instance = allocator.allocate();
        instance.set(value);
        return instance;
    }

    public static Integer allocate(long value) {
        Integer instance = allocator.allocate();
        instance.set(value);
        return instance;
    }

    private Integer() {
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
        return Long.hashCode(value);
    }

    @Override
    public java.lang.String toString() {
        return Long.toString(value);
    }

    @Override
    public int compareTo(Number other) {
        if (other instanceof Integer) {
            return Long.compare(((Integer) other).value, value);
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
