package eu.menzani.lang;

public class Cast {
    public static String toString(float value) {
        int cast = (int) value;
        if (cast == value) {
            return Integer.toString(cast);
        }
        return Float.toString(value);
    }

    public static String toString(double value) {
        long cast = (long) value;
        if (cast == value) {
            return Long.toString(cast);
        }
        return Double.toString(value);
    }

    public static int withoutOverflow(long value) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ArithmeticException();
        }
        return (int) value;
    }

    public static float withoutOverflow(double value) {
        if (value < Float.MIN_VALUE || value > Float.MAX_VALUE) {
            throw new ArithmeticException();
        }
        return (float) value;
    }
}
