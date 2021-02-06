package eu.menzani.atomic;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;

public class AtomicDouble {
    public static void setPlain(Object instance, long offset, double value) {
        UNSAFE.putDouble(instance, offset, value);
    }

    public static void setOpaque(Object instance, long offset, double value) {
        UNSAFE.putDoubleOpaque(instance, offset, value);
    }

    public static void setRelease(Object instance, long offset, double value) {
        UNSAFE.putDoubleRelease(instance, offset, value);
    }

    public static void setVolatile(Object instance, long offset, double value) {
        UNSAFE.putDoubleVolatile(instance, offset, value);
    }

    public static double getPlain(Object instance, long offset) {
        return UNSAFE.getDouble(instance, offset);
    }

    public static double getOpaque(Object instance, long offset) {
        return UNSAFE.getDoubleOpaque(instance, offset);
    }

    public static double getAcquire(Object instance, long offset) {
        return UNSAFE.getDoubleAcquire(instance, offset);
    }

    public static double getVolatile(Object instance, long offset) {
        return UNSAFE.getDoubleVolatile(instance, offset);
    }

    public static boolean compareAndSetVolatile(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.compareAndSetDouble(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoublePlain(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoubleRelease(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDoubleAcquire(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.weakCompareAndSetDouble(instance, offset, oldValue, newValue);
    }

    public static double getAndIncrementPlain(Object instance, long offset) {
        double oldValue = UNSAFE.getDouble(instance, offset);
        UNSAFE.putDouble(instance, offset, oldValue + 1D);
        return oldValue;
    }

    public static double getPlainAndIncrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, 1D);
    }

    public static double getAcquireAndIncrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, 1D);
    }

    public static double getAndIncrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddDouble(instance, offset, 1D);
    }

    public static double getAndDecrementPlain(Object instance, long offset) {
        double oldValue = UNSAFE.getDouble(instance, offset);
        UNSAFE.putDouble(instance, offset, oldValue - 1D);
        return oldValue;
    }

    public static double getPlainAndDecrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, -1D);
    }

    public static double getAcquireAndDecrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, -1D);
    }

    public static double getAndDecrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddDouble(instance, offset, -1D);
    }

    public static double incrementAndGetPlain(Object instance, long offset) {
        double newValue = UNSAFE.getDouble(instance, offset) + 1D;
        UNSAFE.putDouble(instance, offset, newValue);
        return newValue;
    }

    public static double incrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, 1D) + 1D;
    }

    public static double incrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, 1D) + 1D;
    }

    public static double incrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddDouble(instance, offset, 1D) + 1D;
    }

    public static void incrementPlain(Object instance, long offset) {
        UNSAFE.putDouble(instance, offset, UNSAFE.getDouble(instance, offset) + 1D);
    }

    public static void incrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddDoubleRelease(instance, offset, 1D);
    }

    public static void incrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddDoubleAcquire(instance, offset, 1D);
    }

    public static void incrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddDouble(instance, offset, 1D);
    }

    public static double decrementAndGetPlain(Object instance, long offset) {
        double newValue = UNSAFE.getDouble(instance, offset) - 1D;
        UNSAFE.putDouble(instance, offset, newValue);
        return newValue;
    }

    public static double decrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, -1D) - 1D;
    }

    public static double decrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, -1D) - 1D;
    }

    public static double decrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddDouble(instance, offset, -1D) - 1D;
    }

    public static void decrementPlain(Object instance, long offset) {
        UNSAFE.putDouble(instance, offset, UNSAFE.getDouble(instance, offset) - 1D);
    }

    public static void decrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddDoubleRelease(instance, offset, -1D);
    }

    public static void decrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddDoubleAcquire(instance, offset, -1D);
    }

    public static void decrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddDouble(instance, offset, -1D);
    }

    public static double getAndAddPlain(Object instance, long offset, double value) {
        double oldValue = UNSAFE.getDouble(instance, offset);
        UNSAFE.putDouble(instance, offset, oldValue + value);
        return oldValue;
    }

    public static double getPlainAndAddRelease(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, value);
    }

    public static double getAcquireAndAddPlain(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, value);
    }

    public static double getAndAddVolatile(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDouble(instance, offset, value);
    }

    public static double addAndGetPlain(Object instance, long offset, double value) {
        double newValue = UNSAFE.getDouble(instance, offset) + value;
        UNSAFE.putDouble(instance, offset, newValue);
        return newValue;
    }

    public static double addReleaseAndGetPlain(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDoubleRelease(instance, offset, value) + value;
    }

    public static double addPlainAndGetAcquire(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDoubleAcquire(instance, offset, value) + value;
    }

    public static double addAndGetVolatile(Object instance, long offset, double value) {
        return UNSAFE.getAndAddDouble(instance, offset, value) + value;
    }

    public static void addPlain(Object instance, long offset, double value) {
        UNSAFE.putDouble(instance, offset, UNSAFE.getDouble(instance, offset) + value);
    }

    public static void addPlainRelease(Object instance, long offset, double value) {
        UNSAFE.getAndAddDoubleRelease(instance, offset, value);
    }

    public static void addAcquirePlain(Object instance, long offset, double value) {
        UNSAFE.getAndAddDoubleAcquire(instance, offset, value);
    }

    public static void addVolatile(Object instance, long offset, double value) {
        UNSAFE.getAndAddDouble(instance, offset, value);
    }

    public static double getPlainAndSetRelease(Object instance, long offset, double value) {
        return UNSAFE.getAndSetDoubleRelease(instance, offset, value);
    }

    public static double getAcquireAndSetPlain(Object instance, long offset, double value) {
        return UNSAFE.getAndSetDoubleAcquire(instance, offset, value);
    }

    public static double getAndSetVolatile(Object instance, long offset, double value) {
        return UNSAFE.getAndSetDouble(instance, offset, value);
    }

    public static double comparePlainAndExchangeRelease(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDoubleRelease(instance, offset, oldValue, newValue);
    }

    public static double compareAcquireAndExchangePlain(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDoubleAcquire(instance, offset, oldValue, newValue);
    }

    public static double compareAndExchangeVolatile(Object instance, long offset, double oldValue, double newValue) {
        return UNSAFE.compareAndExchangeDouble(instance, offset, oldValue, newValue);
    }

    public static double getAndUpdate(Object instance, long offset, DoubleUnaryOperator updateFunction) {
        double prev = UNSAFE.getDoubleVolatile(instance, offset), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsDouble(prev);
            if (UNSAFE.weakCompareAndSetDouble(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(instance, offset)));
        }
    }

    public static double updateAndGet(Object instance, long offset, DoubleUnaryOperator updateFunction) {
        double prev = UNSAFE.getDoubleVolatile(instance, offset), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsDouble(prev);
            if (UNSAFE.weakCompareAndSetDouble(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(instance, offset)));
        }
    }

    public static double getAndAccumulate(Object instance, long offset, double constant, DoubleBinaryOperator accumulatorFunction) {
        double prev = UNSAFE.getDoubleVolatile(instance, offset), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsDouble(prev, constant);
            if (UNSAFE.weakCompareAndSetDouble(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(instance, offset)));
        }
    }

    public static double accumulateAndGet(Object instance, long offset, double constant, DoubleBinaryOperator accumulatorFunction) {
        double prev = UNSAFE.getDoubleVolatile(instance, offset), next = 0D;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsDouble(prev, constant);
            if (UNSAFE.weakCompareAndSetDouble(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getDoubleVolatile(instance, offset)));
        }
    }
}
