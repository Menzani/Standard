package eu.menzani.atomic;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_INT_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_INT_INDEX_SCALE;

public class AtomicIntArray {
    public static void setPlain(int[] array, int index, int value) {
        UNSAFE.putInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static void setOpaque(int[] array, int index, int value) {
        UNSAFE.putIntOpaque(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static void setRelease(int[] array, int index, int value) {
        UNSAFE.putIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static void setVolatile(int[] array, int index, int value) {
        UNSAFE.putIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getPlain(int[] array, int index) {
        return UNSAFE.getInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index);
    }

    public static int getOpaque(int[] array, int index) {
        return UNSAFE.getIntOpaque(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index);
    }

    public static int getAcquire(int[] array, int index) {
        return UNSAFE.getIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index);
    }

    public static int getVolatile(int[] array, int index) {
        return UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index);
    }

    public static boolean compareAndSetVolatile(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.compareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.weakCompareAndSetIntPlain(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.weakCompareAndSetIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.weakCompareAndSetIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.weakCompareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static int getPlainAndIncrementRelease(int[] array, int index) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static int getAcquireAndIncrementPlain(int[] array, int index) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static int getAndIncrementVolatile(int[] array, int index) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static int getPlainAndDecrementRelease(int[] array, int index) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static int getAcquireAndDecrementPlain(int[] array, int index) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static int getAndDecrementVolatile(int[] array, int index) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static int incrementReleaseAndGetPlain(int[] array, int index) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1) + 1;
    }

    public static int incrementPlainAndGetAcquire(int[] array, int index) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1) + 1;
    }

    public static int incrementAndGetVolatile(int[] array, int index) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1) + 1;
    }

    public static void incrementPlainRelease(int[] array, int index) {
        UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static void incrementAcquirePlain(int[] array, int index) {
        UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static void incrementVolatile(int[] array, int index) {
        UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, 1);
    }

    public static int decrementReleaseAndGetPlain(int[] array, int index) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1) - 1;
    }

    public static int decrementPlainAndGetAcquire(int[] array, int index) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1) - 1;
    }

    public static int decrementAndGetVolatile(int[] array, int index) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1) - 1;
    }

    public static void decrementPlainRelease(int[] array, int index) {
        UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static void decrementAcquirePlain(int[] array, int index) {
        UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static void decrementVolatile(int[] array, int index) {
        UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, -1);
    }

    public static int getPlainAndAddRelease(int[] array, int index, int value) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getAcquireAndAddPlain(int[] array, int index, int value) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getAndAddVolatile(int[] array, int index, int value) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int addReleaseAndGetPlain(int[] array, int index, int value) {
        return UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value) + value;
    }

    public static int addPlainAndGetAcquire(int[] array, int index, int value) {
        return UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value) + value;
    }

    public static int addAndGetVolatile(int[] array, int index, int value) {
        return UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value) + value;
    }

    public static void addPlainRelease(int[] array, int index, int value) {
        UNSAFE.getAndAddIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static void addAcquirePlain(int[] array, int index, int value) {
        UNSAFE.getAndAddIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static void addVolatile(int[] array, int index, int value) {
        UNSAFE.getAndAddInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getPlainAndSetRelease(int[] array, int index, int value) {
        return UNSAFE.getAndSetIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getAcquireAndSetPlain(int[] array, int index, int value) {
        return UNSAFE.getAndSetIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int getAndSetVolatile(int[] array, int index, int value) {
        return UNSAFE.getAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, value);
    }

    public static int comparePlainAndExchangeRelease(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.compareAndExchangeIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static int compareAcquireAndExchangePlain(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.compareAndExchangeIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static int compareAndExchangeVolatile(int[] array, int index, int oldValue, int newValue) {
        return UNSAFE.compareAndExchangeInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static int getPlainAndBitwiseAndRelease(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseAndIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAcquireAndBitwiseAndPlain(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseAndIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAndBitwiseAndVolatile(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseAndInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getPlainAndBitwiseOrRelease(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseOrIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAcquireAndBitwiseOrPlain(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseOrIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAndBitwiseOrVolatile(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseOrInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getPlainAndBitwiseXorRelease(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseXorIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAcquireAndBitwiseXorPlain(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseXorIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAndBitwiseXorVolatile(int[] array, int index, int mask) {
        return UNSAFE.getAndBitwiseXorInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, mask);
    }

    public static int getAndUpdate(int[] array, int index, IntUnaryOperator updateFunction) {
        int prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index), next = 0;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsInt(prev);
            if (UNSAFE.weakCompareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index)));
        }
    }

    public static int updateAndGet(int[] array, int index, IntUnaryOperator updateFunction) {
        int prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index), next = 0;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsInt(prev);
            if (UNSAFE.weakCompareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index)));
        }
    }

    public static int getAndAccumulate(int[] array, int index, int constant, IntBinaryOperator accumulatorFunction) {
        int prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index), next = 0;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsInt(prev, constant);
            if (UNSAFE.weakCompareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index)));
        }
    }

    public static int accumulateAndGet(int[] array, int index, int constant, IntBinaryOperator accumulatorFunction) {
        int prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index), next = 0;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsInt(prev, constant);
            if (UNSAFE.weakCompareAndSetInt(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * index)));
        }
    }

    public static void fillOpaque(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putIntOpaque(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i, value);
        }
    }

    public static void fillRelease(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putIntRelease(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i, value);
        }
    }

    public static void fillVolatile(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getIntOpaque(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getIntAcquire(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getIntVolatile(array, ARRAY_INT_BASE_OFFSET + ARRAY_INT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
