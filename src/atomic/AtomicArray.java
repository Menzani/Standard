package eu.menzani.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_OBJECT_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_OBJECT_INDEX_SCALE;

public class AtomicArray {
    public static <T> void setPlain(T[] array, int index, T value) {
        UNSAFE.putObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    public static <T> void setOpaque(T[] array, int index, T value) {
        UNSAFE.putObjectOpaque(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    public static <T> void setRelease(T[] array, int index, T value) {
        UNSAFE.putObjectRelease(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    public static <T> void setVolatile(T[] array, int index, T value) {
        UNSAFE.putObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPlain(T[] array, int index) {
        return (T) UNSAFE.getObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOpaque(T[] array, int index) {
        return (T) UNSAFE.getObjectOpaque(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAcquire(T[] array, int index) {
        return (T) UNSAFE.getObjectAcquire(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getVolatile(T[] array, int index) {
        return (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index);
    }

    public static <T> boolean compareAndSetVolatile(T[] array, int index, T oldValue, T newValue) {
        return UNSAFE.compareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static <T> boolean weakComparePlainAndSetPlain(T[] array, int index, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectPlain(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static <T> boolean weakComparePlainAndSetRelease(T[] array, int index, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectRelease(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static <T> boolean weakCompareAcquireAndSetPlain(T[] array, int index, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectAcquire(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    public static <T> boolean weakCompareAndSetVolatile(T[] array, int index, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPlainAndSetRelease(T[] array, int index, T value) {
        return (T) UNSAFE.getAndSetObjectRelease(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAcquireAndSetPlain(T[] array, int index, T value) {
        return (T) UNSAFE.getAndSetObjectAcquire(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndSetVolatile(T[] array, int index, T value) {
        return (T) UNSAFE.getAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T comparePlainAndExchangeRelease(T[] array, int index, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObjectRelease(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T compareAcquireAndExchangePlain(T[] array, int index, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObjectAcquire(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T compareAndExchangeVolatile(T[] array, int index, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndUpdate(T[] array, int index, UnaryOperator<T> updateFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.apply(prev);
            if (UNSAFE.weakCompareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T updateAndGet(T[] array, int index, UnaryOperator<T> updateFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.apply(prev);
            if (UNSAFE.weakCompareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndAccumulate(T[] array, int index, T constant, BinaryOperator<T> accumulatorFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.apply(prev, constant);
            if (UNSAFE.weakCompareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, prev, next))
                return prev;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T accumulateAndGet(T[] array, int index, T constant, BinaryOperator<T> accumulatorFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.apply(prev, constant);
            if (UNSAFE.weakCompareAndSetObject(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index, prev, next))
                return next;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * index)));
        }
    }

    public static <T> void fillOpaque(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putObjectOpaque(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i, value);
        }
    }

    public static <T> void fillRelease(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putObjectRelease(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i, value);
        }
    }

    public static <T> void fillVolatile(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            UNSAFE.putObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i, value);
        }
    }

    public static String toStringOpaque(Object[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getObjectOpaque(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringAcquire(Object[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getObjectAcquire(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static String toStringVolatile(Object[] array) {
        int lengthMinusOne = array.length - 1;
        if (lengthMinusOne == -1) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; ; i++) {
            builder.append(UNSAFE.getObjectVolatile(array, ARRAY_OBJECT_BASE_OFFSET + ARRAY_OBJECT_INDEX_SCALE * i));
            if (i == lengthMinusOne) {
                builder.append(']');
                return builder.toString();
            }
            builder.append(", ");
        }
    }
}
