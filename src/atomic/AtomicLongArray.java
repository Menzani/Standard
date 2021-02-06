package eu.menzani.atomic;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_LONG_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_LONG_INDEX_SCALE;

public class AtomicLongArray {
    public static void setPlain(long[] array, int index, long value) {
        UNSAFE.putLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static void setOpaque(long[] array, int index, long value) {
        UNSAFE.putLongOpaque(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static void setRelease(long[] array, int index, long value) {
        UNSAFE.putLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static void setVolatile(long[] array, int index, long value) {
        UNSAFE.putLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getPlain(long[] array, int index) {
        return UNSAFE.getLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index);
    }

    public static long getOpaque(long[] array, int index) {
        return UNSAFE.getLongOpaque(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index);
    }

    public static long getAcquire(long[] array, int index) {
        return UNSAFE.getLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index);
    }

    public static long getVolatile(long[] array, int index) {
        return UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index);
    }

    public static boolean compareAndSetVolatile(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.compareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongPlain(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static long getPlainAndIncrementRelease(long[] array, int index) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static long getAcquireAndIncrementPlain(long[] array, int index) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static long getAndIncrementVolatile(long[] array, int index) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static long getPlainAndDecrementRelease(long[] array, int index) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static long getAcquireAndDecrementPlain(long[] array, int index) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static long getAndDecrementVolatile(long[] array, int index) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static long incrementReleaseAndGetPlain(long[] array, int index) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L) + 1L;
    }

    public static long incrementPlainAndGetAcquire(long[] array, int index) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L) + 1L;
    }

    public static long incrementAndGetVolatile(long[] array, int index) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L) + 1L;
    }

    public static void incrementPlainRelease(long[] array, int index) {
        UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static void incrementAcquirePlain(long[] array, int index) {
        UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static void incrementVolatile(long[] array, int index) {
        UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, 1L);
    }

    public static long decrementReleaseAndGetPlain(long[] array, int index) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L) - 1L;
    }

    public static long decrementPlainAndGetAcquire(long[] array, int index) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L) - 1L;
    }

    public static long decrementAndGetVolatile(long[] array, int index) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L) - 1L;
    }

    public static void decrementPlainRelease(long[] array, int index) {
        UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static void decrementAcquirePlain(long[] array, int index) {
        UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static void decrementVolatile(long[] array, int index) {
        UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, -1L);
    }

    public static long getPlainAndAddRelease(long[] array, int index, long value) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getAcquireAndAddPlain(long[] array, int index, long value) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getAndAddVolatile(long[] array, int index, long value) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long addReleaseAndGetPlain(long[] array, int index, long value) {
        return UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value) + value;
    }

    public static long addPlainAndGetAcquire(long[] array, int index, long value) {
        return UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value) + value;
    }

    public static long addAndGetVolatile(long[] array, int index, long value) {
        return UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value) + value;
    }

    public static void addPlainRelease(long[] array, int index, long value) {
        UNSAFE.getAndAddLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static void addAcquirePlain(long[] array, int index, long value) {
        UNSAFE.getAndAddLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static void addVolatile(long[] array, int index, long value) {
        UNSAFE.getAndAddLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getPlainAndSetRelease(long[] array, int index, long value) {
        return UNSAFE.getAndSetLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getAcquireAndSetPlain(long[] array, int index, long value) {
        return UNSAFE.getAndSetLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long getAndSetVolatile(long[] array, int index, long value) {
        return UNSAFE.getAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, value);
    }

    public static long comparePlainAndExchangeRelease(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static long compareAcquireAndExchangePlain(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static long compareAndExchangeVolatile(long[] array, int index, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, oldValue, newValue);
    }

    public static long getPlainAndBitwiseAndRelease(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseAndLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAcquireAndBitwiseAndPlain(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseAndLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAndBitwiseAndVolatile(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseAndLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getPlainAndBitwiseOrRelease(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseOrLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAcquireAndBitwiseOrPlain(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseOrLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAndBitwiseOrVolatile(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseOrLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getPlainAndBitwiseXorRelease(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseXorLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAcquireAndBitwiseXorPlain(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseXorLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAndBitwiseXorVolatile(long[] array, int index, long mask) {
        return UNSAFE.getAndBitwiseXorLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, mask);
    }

    public static long getAndUpdate(long[] array, int index, LongUnaryOperator updateFunction) {
        long prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsLong(prev);
            if (UNSAFE.weakCompareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index)));
        }
    }

    public static long updateAndGet(long[] array, int index, LongUnaryOperator updateFunction) {
        long prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsLong(prev);
            if (UNSAFE.weakCompareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index)));
        }
    }

    public static long getAndAccumulate(long[] array, int index, long constant, LongBinaryOperator accumulatorFunction) {
        long prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsLong(prev, constant);
            if (UNSAFE.weakCompareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index)));
        }
    }

    public static long accumulateAndGet(long[] array, int index, long constant, LongBinaryOperator accumulatorFunction) {
        long prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsLong(prev, constant);
            if (UNSAFE.weakCompareAndSetLong(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * index)));
        }
    }

    public static void fillOpaque(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putLongOpaque(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i, value);
        }
    }

    public static void fillRelease(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putLongRelease(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i, value);
        }
    }

    public static void fillVolatile(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(long[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getLongOpaque(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(long[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getLongAcquire(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(long[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getLongVolatile(array, ARRAY_LONG_BASE_OFFSET + ARRAY_LONG_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
