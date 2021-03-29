package eu.menzani.lang;

import java.util.Objects;

public class Ensure {
    public static void notEqual(int left, int right) {
        if (left == right) {
            throw new IllegalStateException();
        }
    }

    public static void notEqualTo(int left, int right) {
        if (left == right) {
            throw new IllegalIntStateException(left);
        }
    }

    public static void notEqual(long left, long right) {
        if (left == right) {
            throw new IllegalStateException();
        }
    }

    public static void notEqualTo(long left, long right) {
        if (left == right) {
            throw new IllegalLongStateException(left);
        }
    }

    public static void notEqual(Object left, Object right) {
        if (Objects.equals(left, right)) {
            throw new IllegalStateException();
        }
    }

    public static void notEqualTo(Object left, Object right) {
        if (Objects.equals(left, right)) {
            throw new IllegalObjectStateException(left);
        }
    }

    public static void lesser(int value, int cap) {
        if (value >= cap) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void lesserThan(int value, int cap) {
        if (value >= cap) {
            throw new IllegalIntStateException(value, cap);
        }
    }

    public static void lesser(long value, long cap) {
        if (value >= cap) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void lesserThan(long value, long cap) {
        if (value >= cap) {
            throw new IllegalLongStateException(value, cap);
        }
    }

    public static void notLesser(int value, int cap) {
        if (value < cap) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void notLesserThan(int value, int cap) {
        if (value < cap) {
            throw new IllegalIntStateException(value, cap);
        }
    }

    public static void notLesser(long value, long cap) {
        if (value < cap) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void notLesserThan(long value, long cap) {
        if (value < cap) {
            throw new IllegalLongStateException(value, cap);
        }
    }

    public static void greater(int value, int cap) {
        if (value <= cap) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void greaterThan(int value, int cap) {
        if (value <= cap) {
            throw new IllegalIntStateException(value, cap);
        }
    }

    public static void greater(long value, long cap) {
        if (value <= cap) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void greaterThan(long value, long cap) {
        if (value <= cap) {
            throw new IllegalLongStateException(value, cap);
        }
    }

    public static void notGreater(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void notGreaterThan(int value, int cap) {
        if (value > cap) {
            throw new IllegalIntStateException(value, cap);
        }
    }

    public static void notGreater(long value, long cap) {
        if (value > cap) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void notGreaterThan(long value, long cap) {
        if (value > cap) {
            throw new IllegalLongStateException(value, cap);
        }
    }

    public static void positive(int value) {
        if (value <= 0) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void positive(long value) {
        if (value <= 0L) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void notPositive(int value) {
        if (value > 0) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void notPositive(long value) {
        if (value > 0L) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void negative(int value) {
        if (value >= 0) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void negative(long value) {
        if (value >= 0L) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void notNegative(int value) {
        if (value < 0) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void notNegative(long value) {
        if (value < 0L) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void between(int value, int lowerBound, int upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalIntStateException(value);
        }
    }

    public static void between(long value, long lowerBound, long upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalLongStateException(value);
        }
    }

    public static void between(float value, float lowerBound, float upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalFloatStateException(value);
        }
    }

    public static void between(double value, double lowerBound, double upperBound) {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalDoubleStateException(value);
        }
    }

    public static void notZero(int value) {
        if (value == 0) {
            throw new IllegalStateException();
        }
    }

    public static void notZero(long value) {
        if (value == 0L) {
            throw new IllegalStateException();
        }
    }

    public static void notNull(Object value) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    static final String exceptionClassName = IllegalStateException.class.getName() + ": ";

    private static class IllegalIntStateException extends IllegalStateException {
        private static final long serialVersionUID = 0L;

        IllegalIntStateException(int value) {
            super(exceptionClassName + value);
        }

        IllegalIntStateException(int value, int cap) {
            super(exceptionClassName + value + " " + cap);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalLongStateException extends IllegalStateException {
        private static final long serialVersionUID = 0L;

        IllegalLongStateException(long value) {
            super(exceptionClassName + value);
        }

        IllegalLongStateException(long value, long cap) {
            super(exceptionClassName + value + " " + cap);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalFloatStateException extends IllegalStateException {
        private static final long serialVersionUID = 0L;

        IllegalFloatStateException(float value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalDoubleStateException extends IllegalStateException {
        private static final long serialVersionUID = 0L;

        IllegalDoubleStateException(double value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    private static class IllegalObjectStateException extends IllegalStateException {
        private static final long serialVersionUID = 0L;

        IllegalObjectStateException(Object value) {
            super(exceptionClassName + value);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }
}
