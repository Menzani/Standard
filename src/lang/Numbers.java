package eu.menzani.lang;

public class Numbers {
    public static int ceilDiv(int dividend, int divisor) {
        return (dividend - 1) / divisor + 1;
    }

    public static int getNextPowerOfTwo(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        if (value == highestOneBit) {
            return value;
        }
        return highestOneBit << 1;
    }

    public static long getNextPowerOfTwo(long value) {
        long highestOneBit = Long.highestOneBit(value);
        if (value == highestOneBit) {
            return value;
        }
        return highestOneBit << 1;
    }

    public static boolean isPowerOfTwo(int value) {
        return (value & -value) == value;
    }

    public static boolean isPowerOfTwo(long value) {
        return (value & -value) == value;
    }

    public static boolean isNotPowerOfTwo(int value) {
        return (value & -value) != value;
    }

    public static boolean isNotPowerOfTwo(long value) {
        return (value & -value) != value;
    }

    public static boolean isEven(int value) {
        return (value & 1) == 0;
    }

    public static boolean isOdd(int value) {
        return (value & 1) != 0;
    }
}
