package eu.menzani.lang;

import eu.menzani.struct.Strings;

import java.util.Objects;
import java.util.function.Supplier;

public class Assert {
    public static void equal(char left, char right) {
        assert left == right : left;
    }

    public static void equalTo(char left, char right) {
        assert left == right : left + " " + right;
    }

    public static void equal(int left, int right) {
        assert left == right : left;
    }

    public static void equalTo(int left, int right) {
        assert left == right : left + " " + right;
    }

    public static void equal(long left, long right) {
        assert left == right : left;
    }

    public static void equalTo(long left, long right) {
        assert left == right : left + " " + right;
    }

    public static void equal(Object left, Object right) {
        assert Objects.equals(left, right) : left;
    }

    public static void equalTo(Object left, Object right) {
        assert Objects.equals(left, right) : left + " " + right;
    }

    public static void equalMultiline(Object left, Object right) {
        assert Objects.equals(left, right) : equalMultiline_message(left);
    }

    private static String equalMultiline_message(Object left) {
        return Strings.LN + Strings.LN + left + Strings.LN;
    }

    public static void equalToMultiline(Object left, Object right) {
        assert Objects.equals(left, right) : equalToMultiline_message(left, right);
    }

    private static String equalToMultiline_message(Object left, Object right) {
        return Strings.LN + Strings.LN + left + Strings.LN + Strings.LN + right + Strings.LN;
    }

    public static void same(Object left, Object right) {
        assert left == right : left;
    }

    public static void sameTo(Object left, Object right) {
        assert left == right : left + " " + right;
    }

    public static void notEqual(int left, int right) {
        assert left != right;
    }

    public static void notEqualTo(int left, int right) {
        assert left != right : left;
    }

    public static void notEqual(long left, long right) {
        assert left != right;
    }

    public static void notEqualTo(long left, long right) {
        assert left != right : left;
    }

    public static void notEqual(Object left, Object right) {
        assert !Objects.equals(left, right);
    }

    public static void notEqualTo(Object left, Object right) {
        assert !Objects.equals(left, right) : left;
    }

    public static void notSame(Object left, Object right) {
        assert left != right;
    }

    public static void notSameTo(Object left, Object right) {
        assert left != right : left;
    }

    public static void lesser(int value, int cap) {
        assert value < cap : value;
    }

    public static void lesserThan(int value, int cap) {
        assert value < cap : value + " " + cap;
    }

    public static void lesser(long value, long cap) {
        assert value < cap : value;
    }

    public static void lesserThan(long value, long cap) {
        assert value < cap : value + " " + cap;
    }

    public static void notLesser(int value, int cap) {
        assert value >= cap : value;
    }

    public static void notLesserThan(int value, int cap) {
        assert value >= cap : value + " " + cap;
    }

    public static void notLesser(long value, long cap) {
        assert value >= cap : value;
    }

    public static void notLesserThan(long value, long cap) {
        assert value >= cap : value + " " + cap;
    }

    public static void greater(int value, int cap) {
        assert value > cap : value;
    }

    public static void greaterThan(int value, int cap) {
        assert value > cap : value + " " + cap;
    }

    public static void greater(long value, long cap) {
        assert value > cap : value;
    }

    public static void greaterThan(long value, long cap) {
        assert value > cap : value + " " + cap;
    }

    public static void notGreater(int value, int cap) {
        assert value <= cap : value;
    }

    public static void notGreaterThan(int value, int cap) {
        assert value <= cap : value + " " + cap;
    }

    public static void notGreater(long value, long cap) {
        assert value <= cap : value;
    }

    public static void notGreaterThan(long value, long cap) {
        assert value <= cap : value + " " + cap;
    }

    public static void positive(int value) {
        assert value > 0 : value;
    }

    public static void positive(long value) {
        assert value > 0L : value;
    }

    public static void notPositive(int value) {
        assert value <= 0 : value;
    }

    public static void notPositive(long value) {
        assert value <= 0L : value;
    }

    public static void negative(int value) {
        assert value < 0 : value;
    }

    public static void negative(long value) {
        assert value < 0L : value;
    }

    public static void notNegative(int value) {
        assert value >= 0 : value;
    }

    public static void notNegative(long value) {
        assert value >= 0L : value;
    }

    public static void between(int value, int lowerBound, int upperBound) {
        assert value >= lowerBound && value <= upperBound : value;
    }

    public static void between(long value, long lowerBound, long upperBound) {
        assert value >= lowerBound && value <= upperBound : value;
    }

    public static void between(float value, float lowerBound, float upperBound) {
        assert value >= lowerBound && value <= upperBound : value;
    }

    public static void between(double value, double lowerBound, double upperBound) {
        assert value >= lowerBound && value <= upperBound : value;
    }

    public static <T extends Throwable> T fails(FallibleLogic logic, Class<T> exceptionClass) {
        try {
            logic.run();
        } catch (Throwable e) {
            Assert.same(e.getClass(), exceptionClass);
            @SuppressWarnings("unchecked") var temp = (T) e;
            return temp;
        }
        throw new AssertionError();
    }

    public static <T extends Throwable> T fails(FallibleLogic logic, Class<T> exceptionClass, Supplier<?> messageSupplier) {
        try {
            logic.run();
        } catch (Throwable e) {
            assert e.getClass() == exceptionClass : messageSupplier.get();
            @SuppressWarnings("unchecked") var temp = (T) e;
            return temp;
        }
        throw new AssertionError();
    }
}
