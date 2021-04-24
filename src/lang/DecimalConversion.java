package eu.menzani.lang;

import eu.menzani.Native;
import eu.menzani.data.Destination;
import eu.menzani.struct.DirectBuffer;
import eu.menzani.system.Garbage;
import eu.menzani.system.Unsafe;

public class DecimalConversion {
    static {
        Native.init();
    }

    private final long buffer = Unsafe.allocateMemory(24L);

    public DecimalConversion() {
        Garbage.freeMemory(this, buffer);
    }

    public void appendDouble(double value, Destination destination) {
        if (Double.isNaN(value)) {
            destination.append("NaN");
        } else if (value == Double.POSITIVE_INFINITY) {
            destination.append("Infinity");
        } else if (value == Double.NEGATIVE_INFINITY) {
            destination.append("-Infinity");
        } else {
            int length = appendDouble(value, buffer);

            for (int i = 0; i < length; i++) {
                destination.append((char) DirectBuffer.getByte(buffer, i));
            }
        }
    }

    public void appendDouble(double value, StringBuilder builder) {
        if (Double.isNaN(value)) {
            builder.append("NaN");
        } else if (value == Double.POSITIVE_INFINITY) {
            builder.append("Infinity");
        } else if (value == Double.NEGATIVE_INFINITY) {
            builder.append("-Infinity");
        } else {
            readFromBuffer(appendDouble(value, buffer), builder);
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
            readFromBuffer(appendFloat(value, buffer), builder);
        }
    }

    private void readFromBuffer(int length, StringBuilder builder) {
        for (int i = 0; i < length; i++) {
            builder.append((char) DirectBuffer.getByte(buffer, i));
        }
    }

    public double parseDouble(CharSequence charSequence, int from, int to) {
        return parseDouble(buffer, writeToBuffer(charSequence, from, to));
    }

    public float parseFloat(CharSequence charSequence, int from, int to) {
        return parseFloat(buffer, writeToBuffer(charSequence, from, to));
    }

    private int writeToBuffer(CharSequence charSequence, int from, int to) {
        int length = to - from;
        for (int i = 0; i < length; i++) {
            DirectBuffer.putByte(buffer, i, (byte) charSequence.charAt(from + i));
        }
        return length;
    }

    private static native int appendDouble(double value, long dest);

    private static native int appendFloat(float value, long dest);

    private static native double parseDouble(long src, int srcLength);

    private static native float parseFloat(long src, int srcLength);
}
