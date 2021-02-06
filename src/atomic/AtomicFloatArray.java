package eu.menzani.atomic;

import eu.menzani.lang.FloatBinaryOperator;
import eu.menzani.lang.FloatUnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_FLOAT_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_FLOAT_INDEX_SCALE;

public class AtomicFloatArray {
    public static void setPlain(float[] array, int index, float value) {
        UNSAFE.putFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static void setOpaque(float[] array, int index, float value) {
        UNSAFE.putFloatOpaque(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static void setRelease(float[] array, int index, float value) {
        UNSAFE.putFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static void setVolatile(float[] array, int index, float value) {
        UNSAFE.putFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getPlain(float[] array, int index) {
        return UNSAFE.getFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index);
    }

    public static float getOpaque(float[] array, int index) {
        return UNSAFE.getFloatOpaque(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index);
    }

    public static float getAcquire(float[] array, int index) {
        return UNSAFE.getFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index);
    }

    public static float getVolatile(float[] array, int index) {
        return UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index);
    }

    public static boolean compareAndSetVolatile(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.compareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatPlain(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.weakCompareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static float getPlainAndIncrementRelease(float[] array, int index) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static float getAcquireAndIncrementPlain(float[] array, int index) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static float getAndIncrementVolatile(float[] array, int index) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static float getPlainAndDecrementRelease(float[] array, int index) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static float getAcquireAndDecrementPlain(float[] array, int index) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static float getAndDecrementVolatile(float[] array, int index) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static float incrementReleaseAndGetPlain(float[] array, int index) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F) + 1F;
    }

    public static float incrementPlainAndGetAcquire(float[] array, int index) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F) + 1F;
    }

    public static float incrementAndGetVolatile(float[] array, int index) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F) + 1F;
    }

    public static void incrementPlainRelease(float[] array, int index) {
        UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static void incrementAcquirePlain(float[] array, int index) {
        UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static void incrementVolatile(float[] array, int index) {
        UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, 1F);
    }

    public static float decrementReleaseAndGetPlain(float[] array, int index) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F) - 1F;
    }

    public static float decrementPlainAndGetAcquire(float[] array, int index) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F) - 1F;
    }

    public static float decrementAndGetVolatile(float[] array, int index) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F) - 1F;
    }

    public static void decrementPlainRelease(float[] array, int index) {
        UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static void decrementAcquirePlain(float[] array, int index) {
        UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static void decrementVolatile(float[] array, int index) {
        UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, -1F);
    }

    public static float getPlainAndAddRelease(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getAcquireAndAddPlain(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getAndAddVolatile(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float addReleaseAndGetPlain(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value) + value;
    }

    public static float addPlainAndGetAcquire(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value) + value;
    }

    public static float addAndGetVolatile(float[] array, int index, float value) {
        return UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value) + value;
    }

    public static void addPlainRelease(float[] array, int index, float value) {
        UNSAFE.getAndAddFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static void addAcquirePlain(float[] array, int index, float value) {
        UNSAFE.getAndAddFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static void addVolatile(float[] array, int index, float value) {
        UNSAFE.getAndAddFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getPlainAndSetRelease(float[] array, int index, float value) {
        return UNSAFE.getAndSetFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getAcquireAndSetPlain(float[] array, int index, float value) {
        return UNSAFE.getAndSetFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float getAndSetVolatile(float[] array, int index, float value) {
        return UNSAFE.getAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, value);
    }

    public static float comparePlainAndExchangeRelease(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static float compareAcquireAndExchangePlain(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static float compareAndExchangeVolatile(float[] array, int index, float oldValue, float newValue) {
        return UNSAFE.compareAndExchangeFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static float getAndUpdate(float[] array, int index, FloatUnaryOperator updateFunction) {
        float prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsFloat(prev);
            if (UNSAFE.weakCompareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index)));
        }
    }

    public static float updateAndGet(float[] array, int index, FloatUnaryOperator updateFunction) {
        float prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.applyAsFloat(prev);
            if (UNSAFE.weakCompareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index)));
        }
    }

    public static float getAndAccumulate(float[] array, int index, float constant, FloatBinaryOperator accumulatorFunction) {
        float prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsFloat(prev, constant);
            if (UNSAFE.weakCompareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index)));
        }
    }

    public static float accumulateAndGet(float[] array, int index, float constant, FloatBinaryOperator accumulatorFunction) {
        float prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index), next = 0F;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.applyAsFloat(prev, constant);
            if (UNSAFE.weakCompareAndSetFloat(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * index)));
        }
    }

    public static void fillOpaque(float[] array, float value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putFloatOpaque(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i, value);
        }
    }

    public static void fillRelease(float[] array, float value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putFloatRelease(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i, value);
        }
    }

    public static void fillVolatile(float[] array, float value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(float[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getFloatOpaque(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(float[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getFloatAcquire(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(float[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0, iMax = array.length - 1; ; i++) {
            builder.append(UNSAFE.getFloatVolatile(array, ARRAY_FLOAT_BASE_OFFSET + ARRAY_FLOAT_INDEX_SCALE * i));
            if (i == iMax) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
