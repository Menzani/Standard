package eu.menzani.lang;

import eu.menzani.Native;
import eu.menzani.struct.DirectBuffer;
import eu.menzani.system.Garbage;
import eu.menzani.system.Unsafe;

public class DecimalConversion {
    static {
        Native.init();
    }

    private static final int maxCharsInDouble = 24;

    private final long nativeBuffer = Unsafe.allocateMemory(maxCharsInDouble);
    private final char[] heapBuffer = new char[maxCharsInDouble];

    public DecimalConversion() {
        Garbage.freeMemory(this, nativeBuffer);
    }

    public void appendDouble(double value, StringBuilder builder) {
        if (Double.isNaN(value)) {
            builder.append("NaN");
        } else if (value == Double.POSITIVE_INFINITY) {
            builder.append("Infinity");
        } else if (value == Double.NEGATIVE_INFINITY) {
            builder.append("-Infinity");
        } else {
            readFromHeapBuffer(appendDouble(value, nativeBuffer), builder);
        }
    }

    public void appendFloat(float value, StringBuilder builder) {
        if (Float.isNaN(value)) {
            builder.append("NaN");
        } else if (value == Float.POSITIVE_INFINITY) {
            builder.append("Infinity");
        } else if (value == Float.NEGATIVE_INFINITY) {
            builder.append("-Infinity");
        } else {
            readFromHeapBuffer(appendFloat(value, nativeBuffer), builder);
        }
    }

    private void readFromHeapBuffer(int length, StringBuilder builder) {
        for (int i = 0; i < length; i++) {
            heapBuffer[i] = (char) DirectBuffer.getByte(nativeBuffer, i);
        }
        builder.append(heapBuffer, 0, length);
    }

    public double parseDouble(CharSequence charSequence, int from, int to) {
        return parseDouble(nativeBuffer, writeToNativeBuffer(charSequence, from, to));
    }

    public float parseFloat(CharSequence charSequence, int from, int to) {
        return parseFloat(nativeBuffer, writeToNativeBuffer(charSequence, from, to));
    }

    private int writeToNativeBuffer(CharSequence charSequence, int from, int to) {
        int length = to - from;
        for (int i = 0; i < length; i++) {
            DirectBuffer.putByte(nativeBuffer, i, (byte) charSequence.charAt(from + i));
        }
        return length;
    }

    private static native int appendDouble(double value, long dest);

    private static native int appendFloat(float value, long dest);

    private static native double parseDouble(long src, int srcLength);

    private static native float parseFloat(long src, int srcLength);
}
