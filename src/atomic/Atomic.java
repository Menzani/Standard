package eu.menzani.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static eu.menzani.InternalUnsafe.UNSAFE;

public class Atomic {
    public static <T> void setPlain(Object instance, long offset, T value) {
        UNSAFE.putObject(instance, offset, value);
    }

    public static <T> void setOpaque(Object instance, long offset, T value) {
        UNSAFE.putObjectOpaque(instance, offset, value);
    }

    public static <T> void setRelease(Object instance, long offset, T value) {
        UNSAFE.putObjectRelease(instance, offset, value);
    }

    public static <T> void setVolatile(Object instance, long offset, T value) {
        UNSAFE.putObjectVolatile(instance, offset, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPlain(Object instance, long offset) {
        return (T) UNSAFE.getObject(instance, offset);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOpaque(Object instance, long offset) {
        return (T) UNSAFE.getObjectOpaque(instance, offset);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAcquire(Object instance, long offset) {
        return (T) UNSAFE.getObjectAcquire(instance, offset);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getVolatile(Object instance, long offset) {
        return (T) UNSAFE.getObjectVolatile(instance, offset);
    }

    public static <T> boolean compareAndSetVolatile(Object instance, long offset, T oldValue, T newValue) {
        return UNSAFE.compareAndSetObject(instance, offset, oldValue, newValue);
    }

    public static <T> boolean weakComparePlainAndSetPlain(Object instance, long offset, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectPlain(instance, offset, oldValue, newValue);
    }

    public static <T> boolean weakComparePlainAndSetRelease(Object instance, long offset, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectRelease(instance, offset, oldValue, newValue);
    }

    public static <T> boolean weakCompareAcquireAndSetPlain(Object instance, long offset, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObjectAcquire(instance, offset, oldValue, newValue);
    }

    public static <T> boolean weakCompareAndSetVolatile(Object instance, long offset, T oldValue, T newValue) {
        return UNSAFE.weakCompareAndSetObject(instance, offset, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPlainAndSetRelease(Object instance, long offset, T value) {
        return (T) UNSAFE.getAndSetObjectRelease(instance, offset, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAcquireAndSetPlain(Object instance, long offset, T value) {
        return (T) UNSAFE.getAndSetObjectAcquire(instance, offset, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndSetVolatile(Object instance, long offset, T value) {
        return (T) UNSAFE.getAndSetObject(instance, offset, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T comparePlainAndExchangeRelease(Object instance, long offset, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObjectRelease(instance, offset, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T compareAcquireAndExchangePlain(Object instance, long offset, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObjectAcquire(instance, offset, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T compareAndExchangeVolatile(Object instance, long offset, T oldValue, T newValue) {
        return (T) UNSAFE.compareAndExchangeObject(instance, offset, oldValue, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndUpdate(Object instance, long offset, UnaryOperator<T> updateFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(instance, offset), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.apply(prev);
            if (UNSAFE.weakCompareAndSetObject(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(instance, offset)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T updateAndGet(Object instance, long offset, UnaryOperator<T> updateFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(instance, offset), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = updateFunction.apply(prev);
            if (UNSAFE.weakCompareAndSetObject(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(instance, offset)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAndAccumulate(Object instance, long offset, T constant, BinaryOperator<T> accumulatorFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(instance, offset), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.apply(prev, constant);
            if (UNSAFE.weakCompareAndSetObject(instance, offset, prev, next))
                return prev;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(instance, offset)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T accumulateAndGet(Object instance, long offset, T constant, BinaryOperator<T> accumulatorFunction) {
        T prev = (T) UNSAFE.getObjectVolatile(instance, offset), next = null;
        for (boolean haveNext = false; ; ) {
            if (!haveNext)
                next = accumulatorFunction.apply(prev, constant);
            if (UNSAFE.weakCompareAndSetObject(instance, offset, prev, next))
                return next;
            haveNext = (prev == (prev = (T) UNSAFE.getObjectVolatile(instance, offset)));
        }
    }
}
