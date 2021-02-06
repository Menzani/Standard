package eu.menzani.atomic;

import eu.menzani.lang.FloatBinaryOperator;
import eu.menzani.lang.FloatUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;

public class AtomicFloat {
    public static void setPlain(Object instance, long offset, float value) {
        UNSAFE.putFloat(instance, offset, value);
    }

    public static void setOpaque(Object instance, long offset, float value) {
        UNSAFE.putFloatOpaque(instance, offset, value);
    }

    public static void setRelease(Object instance, long offset, float value) {
        UNSAFE.putFloatRelease(instance, offset, value);
    }

    public static void setVolatile(Object instance, long offset, float value) {
        UNSAFE.putFloatVolatile(instance, offset, value);
    }

    public static float getPlain(Object instance, long offset) {
        return UNSAFE.getFloat(instance, offset);
    }

    public static float getOpaque(Object instance, long offset) {
        return UNSAFE.getFloatOpaque(instance, offset);
    }

    public static float getAcquire(Object instance, long offset) {
        return UNSAFE.getFloatAcquire(instance, offset);
    }

    public static float getVolatile(Object instance, long offset) {
        return UNSAFE.getFloatVolatile(instance, offset);
    }

    public static boolean compareAndSetVolatile(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.compareAndSetFloat(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatPlain(instance, offset, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatRelease(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatAcquire(instance, offset, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloat(instance, offset, oldValue, newValue);
    }

    public static float getAndIncrementPlain(Object instance, long offset) {
        float oldValue = UNSAFE.getFloat(instance, offset);
        UNSAFE.putFloat(instance, offset, oldValue + 1F);
        return oldValue;
    }

    public static float getPlainAndIncrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, 1F);
    }

    public static float getAcquireAndIncrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, 1F);
    }

    public static float getAndIncrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddFloat(instance, offset, 1F);
    }

    public static float getAndDecrementPlain(Object instance, long offset) {
        float oldValue = UNSAFE.getFloat(instance, offset);
        UNSAFE.putFloat(instance, offset, oldValue - 1F);
        return oldValue;
    }

    public static float getPlainAndDecrementRelease(Object instance, long offset) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, -1F);
    }

    public static float getAcquireAndDecrementPlain(Object instance, long offset) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, -1F);
    }

    public static float getAndDecrementVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddFloat(instance, offset, -1F);
    }

    public static float incrementAndGetPlain(Object instance, long offset) {
        float newValue = UNSAFE.getFloat(instance, offset) + 1F;
        UNSAFE.putFloat(instance, offset, newValue);
        return newValue;
    }

    public static float incrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, 1F) + 1F;
    }

    public static float incrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, 1F) + 1F;
    }

    public static float incrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddFloat(instance, offset, 1F) + 1F;
    }

    public static void incrementPlain(Object instance, long offset) {
        UNSAFE.putFloat(instance, offset, UNSAFE.getFloat(instance, offset) + 1F);
    }

    public static void incrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddFloatRelease(instance, offset, 1F);
    }

    public static void incrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddFloatAcquire(instance, offset, 1F);
    }

    public static void incrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddFloat(instance, offset, 1F);
    }

    public static float decrementAndGetPlain(Object instance, long offset) {
        float newValue = UNSAFE.getFloat(instance, offset) - 1F;
        UNSAFE.putFloat(instance, offset, newValue);
        return newValue;
    }

    public static float decrementReleaseAndGetPlain(Object instance, long offset) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, -1F) - 1F;
    }

    public static float decrementPlainAndGetAcquire(Object instance, long offset) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, -1F) - 1F;
    }

    public static float decrementAndGetVolatile(Object instance, long offset) {
        return UNSAFE.getAndAddFloat(instance, offset, -1F) - 1F;
    }

    public static void decrementPlain(Object instance, long offset) {
        UNSAFE.putFloat(instance, offset, UNSAFE.getFloat(instance, offset) - 1F);
    }

    public static void decrementPlainRelease(Object instance, long offset) {
        UNSAFE.getAndAddFloatRelease(instance, offset, -1F);
    }

    public static void decrementAcquirePlain(Object instance, long offset) {
        UNSAFE.getAndAddFloatAcquire(instance, offset, -1F);
    }

    public static void decrementVolatile(Object instance, long offset) {
        UNSAFE.getAndAddFloat(instance, offset, -1F);
    }

    public static float getAndAddPlain(Object instance, long offset, float value) {
        float oldValue = UNSAFE.getFloat(instance, offset);
        UNSAFE.putFloat(instance, offset, oldValue + value);
        return oldValue;
    }

    public static float getPlainAndAddRelease(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, value);
    }

    public static float getAcquireAndAddPlain(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, value);
    }

    public static float getAndAddVolatile(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloat(instance, offset, value);
    }

    public static float addAndGetPlain(Object instance, long offset, float value) {
        float newValue = UNSAFE.getFloat(instance, offset) + value;
        UNSAFE.putFloat(instance, offset, newValue);
        return newValue;
    }

    public static float addReleaseAndGetPlain(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloatRelease(instance, offset, value) + value;
    }

    public static float addPlainAndGetAcquire(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloatAcquire(instance, offset, value) + value;
    }

    public static float addAndGetVolatile(Object instance, long offset, float value) {
        return UNSAFE.getAndAddFloat(instance, offset, value) + value;
    }

    public static void addPlain(Object instance, long offset, float value) {
        UNSAFE.putFloat(instance, offset, UNSAFE.getFloat(instance, offset) + value);
    }

    public static void addPlainRelease(Object instance, long offset, float value) {
        UNSAFE.getAndAddFloatRelease(instance, offset, value);
    }

    public static void addAcquirePlain(Object instance, long offset, float value) {
        UNSAFE.getAndAddFloatAcquire(instance, offset, value);
    }

    public static void addVolatile(Object instance, long offset, float value) {
        UNSAFE.getAndAddFloat(instance, offset, value);
    }

    public static float getPlainAndSetRelease(Object instance, long offset, float value) {
        return UNSAFE.getAndSetFloatRelease(instance, offset, value);
    }

    public static float getAcquireAndSetPlain(Object instance, long offset, float value) {
        return UNSAFE.getAndSetFloatAcquire(instance, offset, value);
    }

    public static float getAndSetVolatile(Object instance, long offset, float value) {
        return UNSAFE.getAndSetFloat(instance, offset, value);
    }

    public static float comparePlainAndExchangeRelease(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloatRelease(instance, offset, oldValue, newValue);
    }

    public static float compareAcquireAndExchangePlain(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloatAcquire(instance, offset, oldValue, newValue);
    }

    public static float compareAndExchangeVolatile(Object instance, long offset, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloat(instance, offset, oldValue, newValue);
    }

    public static float getAndUpdate(Object instance, long offset, FloatUnaryOperator updateFunction) {
        float prev = UNSAFE.getFloatVolatile(instance, offset), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsFloat(prev);
            if (UNSAFE.weakCompareAndSetFloat(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(instance, offset)));
        }
    }

    public static float updateAndGet(Object instance, long offset, FloatUnaryOperator updateFunction) {
        float prev = UNSAFE.getFloatVolatile(instance, offset), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsFloat(prev);
            if (UNSAFE.weakCompareAndSetFloat(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(instance, offset)));
        }
    }

    public static float getAndAccumulate(Object instance, long offset, float constant, FloatBinaryOperator accumulatorFunction) {
        float prev = UNSAFE.getFloatVolatile(instance, offset), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsFloat(prev, constant);
            if (UNSAFE.weakCompareAndSetFloat(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(instance, offset)));
        }
    }

    public static float accumulateAndGet(Object instance, long offset, float constant, FloatBinaryOperator accumulatorFunction) {
        float prev = UNSAFE.getFloatVolatile(instance, offset), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsFloat(prev, constant);
            if (UNSAFE.weakCompareAndSetFloat(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(instance, offset)));
        }
    }
}
