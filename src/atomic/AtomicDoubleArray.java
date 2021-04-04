package eu.menzani.atomic;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_DOUBLE_INDEX_SCALE;

public class AtomicDoubleArray {
    public static void setPlain(double[] array, int index, double value) {
        UNSAFE.putDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static void setOpaque(double[] array, int index, double value) {
        UNSAFE.putDoubleOpaque(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static void setRelease(double[] array, int index, double value) {
        UNSAFE.putDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static void setVolatile(double[] array, int index, double value) {
        UNSAFE.putDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getPlain(double[] array, int index) {
        return UNSAFE.getDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index);
    }

    public static double getOpaque(double[] array, int index) {
        return UNSAFE.getDoubleOpaque(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index);
    }

    public static double getAcquire(double[] array, int index) {
        return UNSAFE.getDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index);
    }

    public static double getVolatile(double[] array, int index) {
        return UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index);
    }

    public static boolean compareAndSetVolatile(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.compareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoublePlain(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static double getPlainAndIncrementRelease(double[] array, int index) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static double getAcquireAndIncrementPlain(double[] array, int index) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static double getAndIncrementVolatile(double[] array, int index) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static double getPlainAndDecrementRelease(double[] array, int index) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static double getAcquireAndDecrementPlain(double[] array, int index) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static double getAndDecrementVolatile(double[] array, int index) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static double incrementReleaseAndGetPlain(double[] array, int index) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D) + 1D;
    }

    public static double incrementPlainAndGetAcquire(double[] array, int index) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D) + 1D;
    }

    public static double incrementAndGetVolatile(double[] array, int index) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D) + 1D;
    }

    public static void incrementPlainRelease(double[] array, int index) {
        UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static void incrementAcquirePlain(double[] array, int index) {
        UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static void incrementVolatile(double[] array, int index) {
        UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, 1D);
    }

    public static double decrementReleaseAndGetPlain(double[] array, int index) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D) - 1D;
    }

    public static double decrementPlainAndGetAcquire(double[] array, int index) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D) - 1D;
    }

    public static double decrementAndGetVolatile(double[] array, int index) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D) - 1D;
    }

    public static void decrementPlainRelease(double[] array, int index) {
        UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static void decrementAcquirePlain(double[] array, int index) {
        UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static void decrementVolatile(double[] array, int index) {
        UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, -1D);
    }

    public static double getPlainAndAddRelease(double[] array, int index, double value) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getAcquireAndAddPlain(double[] array, int index, double value) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getAndAddVolatile(double[] array, int index, double value) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double addReleaseAndGetPlain(double[] array, int index, double value) {
        return UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value) + value;
    }

    public static double addPlainAndGetAcquire(double[] array, int index, double value) {
        return UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value) + value;
    }

    public static double addAndGetVolatile(double[] array, int index, double value) {
        return UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value) + value;
    }

    public static void addPlainRelease(double[] array, int index, double value) {
        UNSAFE.getAndAddDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static void addAcquirePlain(double[] array, int index, double value) {
        UNSAFE.getAndAddDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static void addVolatile(double[] array, int index, double value) {
        UNSAFE.getAndAddDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getPlainAndSetRelease(double[] array, int index, double value) {
        return UNSAFE.getAndSetDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getAcquireAndSetPlain(double[] array, int index, double value) {
        return UNSAFE.getAndSetDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double getAndSetVolatile(double[] array, int index, double value) {
        return UNSAFE.getAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, value);
    }

    public static double comparePlainAndExchangeRelease(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static double compareAcquireAndExchangePlain(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static double compareAndExchangeVolatile(double[] array, int index, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, oldValue, newValue);
    }

    public static double getAndUpdate(double[] array, int index, DoubleUnaryOperator updateFunction) {
        double prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsDouble(prev);
            if (UNSAFE.weakCompareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index)));
        }
    }

    public static double updateAndGet(double[] array, int index, DoubleUnaryOperator updateFunction) {
        double prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsDouble(prev);
            if (UNSAFE.weakCompareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index)));
        }
    }

    public static double getAndAccumulate(double[] array, int index, double constant, DoubleBinaryOperator accumulatorFunction) {
        double prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsDouble(prev, constant);
            if (UNSAFE.weakCompareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index)));
        }
    }

    public static double accumulateAndGet(double[] array, int index, double constant, DoubleBinaryOperator accumulatorFunction) {
        double prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsDouble(prev, constant);
            if (UNSAFE.weakCompareAndSetDouble(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * index)));
        }
    }

    public static void fillOpaque(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putDoubleOpaque(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i, value);
        }
    }

    public static void fillRelease(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putDoubleRelease(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i, value);
        }
    }

    public static void fillVolatile(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(double[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getDoubleOpaque(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(double[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getDoubleAcquire(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(double[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getDoubleVolatile(array, ARRAY_DOUBLE_BASE_OFFSET + ARRAY_DOUBLE_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
