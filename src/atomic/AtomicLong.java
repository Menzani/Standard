package eu.menzani.atomic;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;

public class AtomicLong {
    public static void setPlain(Object instance, long offset, long value) {
        UNSAFE.putLong(instance, offset, value);
    }

    public static void setOpaque(Object instance, long offset, long value) {
        UNSAFE.putLongOpaque(instance, offset, value);
    }

    public static void setRelease(Object instance, long offset, long value) {
        UNSAFE.putLongRelease(instance, offset, value);
    }

    public static void setVolatile(Object instance, long offset, long value) {
        UNSAFE.putLongVolatile(instance, offset, value);
    }

    public static long getPlain(Object instance, long offset) {
        return UNSAFE.getLong(instance, offset);
    }

    public static long getOpaque(Object instance, long offset) {
        return UNSAFE.getLongOpaque(instance, offset);
    }

    public static long getAcquire(Object instance, long offset) {
        return UNSAFE.getLongAcquire(instance, offset);
    }

    public static long getVolatile(Object instance, long offset) {
        return UNSAFE.getLongVolatile(instance, offset);
    }

    public static boolean compareAndSetVolatile(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.compareAndSetLong(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongPlain(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongRelease(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLongAcquire(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.weakCompareAndSetLong(instance, offset, oldValue, newValue);
    }

    public static long getAndIncrementPlain(Object instance, long offset) {
        long oldValue = UNSAFE.getLong(instance, offset);
        UNSAFE.putLong(instance, offset, oldValue + 1L);
        return oldValue;
    }

    public static long getPlainAndIncrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddLongRelease(instance, offset, 1L);
    }

    public static long getAcquireAndIncrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, 1L);
    }

    public static long getAndIncrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddLong(instance, offset, 1L);
    }

    public static long getAndDecrementPlain(Object instance, long offset) {
        long oldValue = UNSAFE.getLong(instance, offset);
        UNSAFE.putLong(instance, offset, oldValue - 1L);
        return oldValue;
    }

    public static long getPlainAndDecrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddLongRelease(instance, offset, -1L);
    }

    public static long getAcquireAndDecrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, -1L);
    }

    public static long getAndDecrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddLong(instance, offset, -1L);
    }

    public static long incrementAndGetPlain(Object instance, long offset) {
        long newValue = UNSAFE.getLong(instance, offset) + 1L;
        UNSAFE.putLong(instance, offset, newValue);
        return newValue;
    }

    public static long incrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddLongRelease(instance, offset, 1L) + 1L;
    }

    public static long incrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, 1L) + 1L;
    }

    public static long incrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddLong(instance, offset, 1L) + 1L;
    }

    public static void incrementPlain(Object instance, long offset) {
        UNSAFE.putLong(instance, offset, UNSAFE.getLong(instance, offset) + 1L);
    }

    public static void incrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddLongRelease(instance, offset, 1L);
    }

    public static void incrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddLongAcquire(instance, offset, 1L);
    }

    public static void incrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddLong(instance, offset, 1L);
    }

    public static long decrementAndGetPlain(Object instance, long offset) {
        long newValue = UNSAFE.getLong(instance, offset) - 1L;
        UNSAFE.putLong(instance, offset, newValue);
        return newValue;
    }

    public static long decrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddLongRelease(instance, offset, -1L) - 1L;
    }

    public static long decrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, -1L) - 1L;
    }

    public static long decrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddLong(instance, offset, -1L) - 1L;
    }

    public static void decrementPlain(Object instance, long offset) {
        UNSAFE.putLong(instance, offset, UNSAFE.getLong(instance, offset) - 1L);
    }

    public static void decrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddLongRelease(instance, offset, -1L);
    }

    public static void decrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddLongAcquire(instance, offset, -1L);
    }

    public static void decrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddLong(instance, offset, -1L);
    }

    public static long getAndAddPlain(Object instance, long offset, long value) {
        long oldValue = UNSAFE.getLong(instance, offset);
        UNSAFE.putLong(instance, offset, oldValue + value);
        return oldValue;
    }

    public static long getPlainAndAddRelease(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLongRelease(instance, offset, value);
    }

    public static long getAcquireAndAddPlain(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, value);
    }

    public static long getAndAddVolatile(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLong(instance, offset, value);
    }

    public static long addAndGetPlain(Object instance, long offset, long value) {
        long newValue = UNSAFE.getLong(instance, offset) + value;
        UNSAFE.putLong(instance, offset, newValue);
        return newValue;
    }

    public static long addReleaseAndGetPlain(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLongRelease(instance, offset, value) + value;
    }

    public static long addPlainAndGetAcquire(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLongAcquire(instance, offset, value) + value;
    }

    public static long addAndGetVolatile(Object instance, long offset, long value) {
        return UNSAFE.getAndAddLong(instance, offset, value) + value;
    }

    public static void addPlain(Object instance, long offset, long value) {
        UNSAFE.putLong(instance, offset, UNSAFE.getLong(instance, offset) + value);
    }

    public static void addPlainRelease(Object instance, long offset, long value) {
        UNSAFE.getAndAddLongRelease(instance, offset, value);
    }

    public static void addAcquirePlain(Object instance, long offset, long value) {
        UNSAFE.getAndAddLongAcquire(instance, offset, value);
    }

    public static void addVolatile(Object instance, long offset, long value) {
        UNSAFE.getAndAddLong(instance, offset, value);
    }

    public static long getPlainAndSetRelease(Object instance, long offset, long value) {
        return UNSAFE.getAndSetLongRelease(instance, offset, value);
    }

    public static long getAcquireAndSetPlain(Object instance, long offset, long value) {
        return UNSAFE.getAndSetLongAcquire(instance, offset, value);
    }

    public static long getAndSetVolatile(Object instance, long offset, long value) {
        return UNSAFE.getAndSetLong(instance, offset, value);
    }

    public static long comparePlainAndExchangeRelease(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLongRelease(instance, offset, oldValue, newValue);
    }

    public static long compareAcquireAndExchangePlain(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLongAcquire(instance, offset, oldValue, newValue);
    }

    public static long compareAndExchangeVolatile(Object instance, long offset, long oldValue, long newValue) {
        return UNSAFE.compareAndExchangeLong(instance, offset, oldValue, newValue);
    }

    public static long getPlainAndBitwiseAndRelease(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseAndLongRelease(instance, offset, mask);
    }

    public static long getAcquireAndBitwiseAndPlain(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseAndLongAcquire(instance, offset, mask);
    }

    public static long getAndBitwiseAndVolatile(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseAndLong(instance, offset, mask);
    }

    public static long getPlainAndBitwiseOrRelease(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseOrLongRelease(instance, offset, mask);
    }

    public static long getAcquireAndBitwiseOrPlain(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseOrLongAcquire(instance, offset, mask);
    }

    public static long getAndBitwiseOrVolatile(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseOrLong(instance, offset, mask);
    }

    public static long getPlainAndBitwiseXorRelease(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseXorLongRelease(instance, offset, mask);
    }

    public static long getAcquireAndBitwiseXorPlain(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseXorLongAcquire(instance, offset, mask);
    }

    public static long getAndBitwiseXorVolatile(Object instance, long offset, long mask) {
        return UNSAFE.getAndBitwiseXorLong(instance, offset, mask);
    }

    public static long getAndUpdate(Object instance, long offset, LongUnaryOperator updateFunction) {
        long prev = UNSAFE.getLongVolatile(instance, offset), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsLong(prev);
            if (UNSAFE.weakCompareAndSetLong(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(instance, offset)));
        }
    }

    public static long updateAndGet(Object instance, long offset, LongUnaryOperator updateFunction) {
        long prev = UNSAFE.getLongVolatile(instance, offset), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsLong(prev);
            if (UNSAFE.weakCompareAndSetLong(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(instance, offset)));
        }
    }

    public static long getAndAccumulate(Object instance, long offset, long constant, LongBinaryOperator accumulatorFunction) {
        long prev = UNSAFE.getLongVolatile(instance, offset), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsLong(prev, constant);
            if (UNSAFE.weakCompareAndSetLong(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(instance, offset)));
        }
    }

    public static long accumulateAndGet(Object instance, long offset, long constant, LongBinaryOperator accumulatorFunction) {
        long prev = UNSAFE.getLongVolatile(instance, offset), next = 0L;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsLong(prev, constant);
            if (UNSAFE.weakCompareAndSetLong(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getLongVolatile(instance, offset)));
        }
    }
}
