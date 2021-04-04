package eu.menzani.atomic;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_BOOLEAN_INDEX_SCALE;

public class AtomicBooleanArray {
    public static void setPlain(boolean[] array, int index, boolean value) {
        UNSAFE.putBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static void setOpaque(boolean[] array, int index, boolean value) {
        UNSAFE.putBooleanOpaque(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static void setRelease(boolean[] array, int index, boolean value) {
        UNSAFE.putBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static void setVolatile(boolean[] array, int index, boolean value) {
        UNSAFE.putBooleanVolatile(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static boolean getPlain(boolean[] array, int index) {
        return UNSAFE.getBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index);
    }

    public static boolean getOpaque(boolean[] array, int index) {
        return UNSAFE.getBooleanOpaque(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index);
    }

    public static boolean getAcquire(boolean[] array, int index) {
        return UNSAFE.getBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index);
    }

    public static boolean getVolatile(boolean[] array, int index) {
        return UNSAFE.getBooleanVolatile(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index);
    }

    public static boolean compareAndSetVolatile(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.compareAndSetBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetPlain(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.weakCompareAndSetBooleanPlain(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakComparePlainAndSetRelease(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.weakCompareAndSetBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAcquireAndSetPlain(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.weakCompareAndSetBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean weakCompareAndSetVolatile(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.weakCompareAndSetBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean getPlainAndSetRelease(boolean[] array, int index, boolean value) {
        return UNSAFE.getAndSetBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static boolean getAcquireAndSetPlain(boolean[] array, int index, boolean value) {
        return UNSAFE.getAndSetBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static boolean getAndSetVolatile(boolean[] array, int index, boolean value) {
        return UNSAFE.getAndSetBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, value);
    }

    public static boolean comparePlainAndExchangeRelease(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.compareAndExchangeBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean compareAcquireAndExchangePlain(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.compareAndExchangeBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean compareAndExchangeVolatile(boolean[] array, int index, boolean oldValue, boolean newValue) {
        return UNSAFE.compareAndExchangeBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, oldValue, newValue);
    }

    public static boolean getPlainAndBitwiseAndRelease(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseAndBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAcquireAndBitwiseAndPlain(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseAndBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAndBitwiseAndVolatile(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseAndBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getPlainAndBitwiseOrRelease(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseOrBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAcquireAndBitwiseOrPlain(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseOrBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAndBitwiseOrVolatile(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseOrBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getPlainAndBitwiseXorRelease(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseXorBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAcquireAndBitwiseXorPlain(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseXorBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static boolean getAndBitwiseXorVolatile(boolean[] array, int index, boolean mask) {
        return UNSAFE.getAndBitwiseXorBoolean(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * index, mask);
    }

    public static void fillOpaque(boolean[] array, boolean value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putBooleanOpaque(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i, value);
        }
    }

    public static void fillRelease(boolean[] array, boolean value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putBooleanRelease(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i, value);
        }
    }

    public static void fillVolatile(boolean[] array, boolean value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putBooleanVolatile(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(boolean[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getBooleanOpaque(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(boolean[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getBooleanAcquire(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(boolean[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getBooleanVolatile(array, ARRAY_BOOLEAN_BASE_OFFSET + ARRAY_BOOLEAN_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
