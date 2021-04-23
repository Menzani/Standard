package eu.menzani.struct;

import eu.menzani.lang.Assume;

import static eu.menzani.InternalUnsafe.UNSAFE;
import static eu.menzani.system.Unsafe.ARRAY_BYTE_BASE_OFFSET;
import static eu.menzani.system.Unsafe.ARRAY_BYTE_INDEX_SCALE;

public class HeapBuffer {
    public static final byte[] EMPTY = new byte[0];

    public static byte[] allocate(int length) {
        Assume.notGreater(length, Integer.MAX_VALUE - 8);
        return new byte[length + 8];
    }

    public static void putByte(byte[] array, int index, byte value) {
        UNSAFE.putByte(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putChar(byte[] array, int index, char value) {
        UNSAFE.putChar(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putShort(byte[] array, int index, short value) {
        UNSAFE.putShort(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putInt(byte[] array, int index, int value) {
        UNSAFE.putInt(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putLong(byte[] array, int index, long value) {
        UNSAFE.putLong(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putBoolean(byte[] array, int index, boolean value) {
        UNSAFE.putBoolean(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putFloat(byte[] array, int index, float value) {
        UNSAFE.putFloat(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static void putDouble(byte[] array, int index, double value) {
        UNSAFE.putDouble(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index, value);
    }

    public static byte getByte(byte[] array, int index) {
        return UNSAFE.getByte(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static char getChar(byte[] array, int index) {
        return UNSAFE.getChar(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static short getShort(byte[] array, int index) {
        return UNSAFE.getShort(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static int getInt(byte[] array, int index) {
        return UNSAFE.getInt(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static long getLong(byte[] array, int index) {
        return UNSAFE.getLong(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static boolean getBoolean(byte[] array, int index) {
        return UNSAFE.getBoolean(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static float getFloat(byte[] array, int index) {
        return UNSAFE.getFloat(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }

    public static double getDouble(byte[] array, int index) {
        return UNSAFE.getDouble(array, ARRAY_BYTE_BASE_OFFSET + ARRAY_BYTE_INDEX_SCALE * index);
    }
}
