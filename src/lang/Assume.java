package eu.menzani.lang;

public class Assume {
    public static void notEqual(int left, int right) {
        if (left == right) {
            throw new IllegalArgumentException();
        }
    }

    public static void notEqualTo(int left, int right) {
        if (left == right) {
            throw new IllegalIntArgumentException(left);
        }
    }

    public static void notEqual(long left, long right) {
        if (left == right) {
            throw new IllegalArgumentException();
        }
    }

    public static void notEqualTo(long left, long right) {
        if (left == right) {
            throw new IllegalLongArgumentException(left);
        }
    }

    public static void notEqual(Object left, Object right) {
        if (left == right || (left != null && left.equals(right))) {
            throw new IllegalArgumentException();
        }
    }

    public static void notEqualTo(Object left, Object right) {
        if (left == right || (left != null && left.equals(right))) {
            throw new IllegalObjectArgumentException(left);
        }
    }

    public static void lesser(int value, int cap) {
        if (value >= cap) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void lesserThan(int value, int cap) {
        if (value >= cap) {
            throw new IllegalIntArgumentException(value, cap);
        }
    }

    public static void lesser(long value, long cap) {
        if (value >= cap) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void lesserThan(long value, long cap) {
        if (value >= cap) {
            throw new IllegalLongArgumentException(value, cap);
        }
    }

    public static void lesserOrEqual(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void lesserThanOrEqualTo(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntArgumentException(value, cap);
        }
    }

    public static void notLesser(int value, int cap) {
        if (value < cap) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void notLesserThan(int value, int cap) {
        if (value < cap) {
            throw new IllegalIntArgumentException(value, cap);
        }
    }

    public static void notLesser(long value, long cap) {
        if (value < cap) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void notLesserThan(long value, long cap) {
        if (value < cap) {
            throw new IllegalLongArgumentException(value, cap);
        }
    }

    public static void greater(int value, int cap) {
        if (value <= cap) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void greaterThan(int value, int cap) {
        if (value <= cap) {
            throw new IllegalIntArgumentException(value, cap);
        }
    }

    public static void greater(long value, long cap) {
        if (value <= cap) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void greaterThan(long value, long cap) {
        if (value <= cap) {
            throw new IllegalLongArgumentException(value, cap);
        }
    }

    public static void notGreater(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void notGreaterThan(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntArgumentException(value, cap);
        }
    }

    public static void notGreater(long value, long cap) {
        if (value > cap) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void notGreaterThan(long value, long cap) {
        if (value > cap) {
            throw new IllegalLongArgumentException(value, cap);
        }
    }

    public static void positive(int value) {
        if (value <= 0) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void positive(long value) {
        if (value <= 0L) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void notPositive(int value) {
        if (value > 0) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void notPositive(long value) {
        if (value > 0L) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void negative(int value) {
        if (value >= 0) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void negative(long value) {
        if (value >= 0L) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void notNegative(int value) {
        if (value < 0) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void notNegative(long value) {
        if (value < 0L) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void between(int value, int lowerBound, int upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalIntArgumentException(value);
        }
    }

    public static void between(long value, long lowerBound, long upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalLongArgumentException(value);
        }
    }

    public static void between(float value, float lowerBound, float upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalFloatArgumentException(value);
        }
    }

    public static void between(double value, double lowerBound, double upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalDoubleArgumentException(value);
        }
    }

    public static void notZero(int value) {
        if (value == 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void notZero(long value) {
        if (value == 0L) {
            throw new IllegalArgumentException();
        }
    }

    public static void notNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void powerOfTwo(int value) {
        if (Numbers.isNotPowerOfTwo(value)) {
            throw new IllegalArgumentException();
        }
    }

    public static void powerOfTwo(long value) {
        if (Numbers.isNotPowerOfTwo(value)) {
            throw new IllegalArgumentException();
        }
    }

    public static void even(int value) {
        if (Numbers.isOdd(value)) {
            throw new IllegalArgumentException();
        }
    }

    public static void odd(int value) {
        if (Numbers.isEven(value)) {
            throw new IllegalArgumentException();
        }
    }

    static final String exceptionClassName = IllegalArgumentException.class.getName() + ": ";

    private static class IllegalIntArgumentException extends IllegalArgumentException {
        private static final long serialVersionUID = 0L;

        IllegalIntArgumentException(int value) {
            super(exceptionClassName + value);
        }

        IllegalIntArgumentException(int value, int cap) {
            super(exceptionClassName + value + " " + cap);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalLongArgumentException extends IllegalArgumentException {
        private static final long serialVersionUID = 0L;

        IllegalLongArgumentException(long value) {
            super(exceptionClassName + value);
        }

        IllegalLongArgumentException(long value, long cap) {
            super(exceptionClassName + value + " " + cap);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalFloatArgumentException extends IllegalArgumentException {
        private static final long serialVersionUID = 0L;

        IllegalFloatArgumentException(float value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalDoubleArgumentException extends IllegalArgumentException {
        private static final long serialVersionUID = 0L;

        IllegalDoubleArgumentException(double value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalObjectArgumentException extends IllegalArgumentException {
        private static final long serialVersionUID = 0L;

        IllegalObjectArgumentException(Object value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }
}
