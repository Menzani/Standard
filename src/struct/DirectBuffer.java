package eu.menzani.struct;

import eu.menzani.lang.Assume;
import eu.menzani.system.Unsafe;

import static eu.menzani.InternalUnsafe.UNSAFE;

public class DirectBuffer {
    public static long allocate(long length) {
        Assume.notGreater(length, Long.MAX_VALUE - 8L);
        return Unsafe.allocateMemory(length + 8L);
    }

    public static void putByte(long address, long index, byte value) {
        UNSAFE.putByte(address + index, value);
    }

    public static void putChar(long address, long index, char value) {
        UNSAFE.putChar(address + index, value);
    }

    public static void putShort(long address, long index, short value) {
        UNSAFE.putShort(address + index, value);
    }

    public static void putInt(long address, long index, int value) {
        UNSAFE.putInt(address + index, value);
    }

    public static void putLong(long address, long index, long value) {
        UNSAFE.putLong(address + index, value);
    }

    public static void putBoolean(long address, long index, boolean value) {
        UNSAFE.putBoolean(null, address + index, value);
    }

    public static void putFloat(long address, long index, float value) {
        UNSAFE.putFloat(address + index, value);
    }

    public static void putDouble(long address, long index, double value) {
        UNSAFE.putDouble(address + index, value);
    }

    public static byte getByte(long address, long index) {
        return UNSAFE.getByte(address + index);
    }

    public static char getChar(long address, long index) {
        return UNSAFE.getChar(address + index);
    }

    public static short getShort(long address, long index) {
        return UNSAFE.getShort(address + index);
    }

    public static int getInt(long address, long index) {
        return UNSAFE.getInt(address + index);
    }

    public static long getLong(long address, long index) {
        return UNSAFE.getLong(address + index);
    }

    public static boolean getBoolean(long address, long index) {
        return UNSAFE.getBoolean(null, address + index);
    }

    public static float getFloat(long address, long index) {
        return UNSAFE.getFloat(address + index);
    }

    public static double getDouble(long address, long index) {
        return UNSAFE.getDouble(address + index);
    }
}
