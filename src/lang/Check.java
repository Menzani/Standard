package eu.menzani.lang;

public class Check {
    public static void equal(char left, char right) {
        if (left != right) {
            throw new AssertionError(left);
        }
    }

    public static void equalTo(char left, char right) {
        if (left != right) {
            throw new CharAssertionError(left, right);
        }
    }

    public static void equal(int left, int right) {
        if (left != right) {
            throw new AssertionError(left);
        }
    }

    public static void equalTo(int left, int right) {
        if (left != right) {
            throw new IntAssertionError(left, right);
        }
    }

    public static void equal(long left, long right) {
        if (left != right) {
            throw new AssertionError(left);
        }
    }

    public static void equalTo(long left, long right) {
        if (left != right) {
            throw new LongAssertionError(left, right);
        }
    }

    public static void equal(Object left, Object right) {
        if (left != right && (left == null || !left.equals(right))) {
            throw new AssertionError(left);
        }
    }

    public static void equalTo(Object left, Object right) {
        if (left != right && (left == null || !left.equals(right))) {
            throw new ObjectAssertionError(left, right);
        }
    }

    public static void notEqual(int left, int right) {
        if (left == right) {
            throw new AssertionError();
        }
    }

    public static void notEqualTo(int left, int right) {
        if (left == right) {
            throw new AssertionError(left);
        }
    }

    public static void notEqual(long left, long right) {
        if (left == right) {
            throw new AssertionError();
        }
    }

    public static void notEqualTo(long left, long right) {
        if (left == right) {
            throw new AssertionError(left);
        }
    }

    public static void notEqual(Object left, Object right) {
        if (left == right || (left != null && left.equals(right))) {
            throw new AssertionError();
        }
    }

    public static void notEqualTo(Object left, Object right) {
        if (left == right || (left != null && left.equals(right))) {
            throw new AssertionError(left);
        }
    }

    public static void lesser(int value, int cap) {
        if (value >= cap) {
            throw new AssertionError(value);
        }
    }

    public static void lesserThan(int value, int cap) {
        if (value >= cap) {
            throw new IntAssertionError(value, cap);
        }
    }

    public static void lesser(long value, long cap) {
        if (value >= cap) {
            throw new AssertionError(value);
        }
    }

    public static void lesserThan(long value, long cap) {
        if (value >= cap) {
            throw new LongAssertionError(value, cap);
        }
    }

    public static void notLesser(int value, int cap) {
        if (value < cap) {
            throw new AssertionError(value);
        }
    }

    public static void notLesserThan(int value, int cap) {
        if (value < cap) {
            throw new IntAssertionError(value, cap);
        }
    }

    public static void notLesser(long value, long cap) {
        if (value < cap) {
            throw new AssertionError(value);
        }
    }

    public static void notLesserThan(long value, long cap) {
        if (value < cap) {
            throw new LongAssertionError(value, cap);
        }
    }

    public static void greater(int value, int cap) {
        if (value <= cap) {
            throw new AssertionError(value);
        }
    }

    public static void greaterThan(int value, int cap) {
        if (value <= cap) {
            throw new IntAssertionError(value, cap);
        }
    }

    public static void greater(long value, long cap) {
        if (value <= cap) {
            throw new AssertionError(value);
        }
    }

    public static void greaterThan(long value, long cap) {
        if (value <= cap) {
            throw new LongAssertionError(value, cap);
        }
    }

    public static void notGreater(int value, int cap) {
        if (value > cap) {
            throw new AssertionError(value);
        }
    }

    public static void notGreaterThan(int value, int cap) {
        if (value > cap) {
            throw new IntAssertionError(value, cap);
        }
    }

    public static void notGreater(long value, long cap) {
        if (value > cap) {
            throw new AssertionError(value);
        }
    }

    public static void notGreaterThan(long value, long cap) {
        if (value > cap) {
            throw new LongAssertionError(value, cap);
        }
    }

    public static void positive(int value) {
        if (value <= 0) {
            throw new AssertionError(value);
        }
    }

    public static void positive(long value) {
        if (value <= 0L) {
            throw new AssertionError(value);
        }
    }

    public static void notPositive(int value) {
        if (value > 0) {
            throw new AssertionError(value);
        }
    }

    public static void notPositive(long value) {
        if (value > 0L) {
            throw new AssertionError(value);
        }
    }

    public static void negative(int value) {
        if (value >= 0) {
            throw new AssertionError(value);
        }
    }

    public static void negative(long value) {
        if (value >= 0L) {
            throw new AssertionError(value);
        }
    }

    public static void notNegative(int value) {
        if (value < 0) {
            throw new AssertionError(value);
        }
    }

    public static void notNegative(long value) {
        if (value < 0L) {
            throw new AssertionError(value);
        }
    }

    public static void between(int value, int lowerBound, int upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new AssertionError(value);
        }
    }

    public static void between(long value, long lowerBound, long upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new AssertionError(value);
        }
    }

    public static void between(float value, float lowerBound, float upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new AssertionError(value);
        }
    }

    public static void between(double value, double lowerBound, double upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new AssertionError(value);
        }
    }

    public static void notZero(int value) {
        if (value == 0) {
            throw new AssertionError();
        }
    }

    public static void notZero(long value) {
        if (value == 0L) {
            throw new AssertionError();
        }
    }

    public static void notNull(Object value) {
        if (value == null) {
            throw new AssertionError();
        }
    }

    static final String exceptionClassName = AssertionError.class.getName() + ": ";

    private static class CharAssertionError extends AssertionError {
        CharAssertionError(char one, char two) {
            super(exceptionClassName + one + " " + two);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IntAssertionError extends AssertionError {
        IntAssertionError(int one, int two) {
            super(exceptionClassName + one + " " + two);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class LongAssertionError extends AssertionError {
        LongAssertionError(long one, long two) {
            super(exceptionClassName + one + " " + two);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class ObjectAssertionError extends AssertionError {
        ObjectAssertionError(Object one, Object two) {
            super(exceptionClassName + one + " " + two);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }
}
