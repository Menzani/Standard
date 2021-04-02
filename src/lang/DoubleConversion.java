package eu.menzani.lang;

import eu.menzani.Native;
import eu.menzani.struct.DirectBuffer;
import eu.menzani.system.Garbage;
import eu.menzani.system.Unsafe;

public class DoubleConversion {
    static {
        Native.init();
    }

    private static final int maxCharsInDouble = 24;

    private final long nativeBuffer = Unsafe.allocateMemory(maxCharsInDouble);
    private final char[] heapBuffer = new char[maxCharsInDouble];

    public DoubleConversion() {
        Garbage.freeMemory(this, nativeBuffer);
    }

    public double parse(CharSequence charSequence, int from, int to) {
        int length = to - from;
        for (int i = 0; i < length; i++) {
            DirectBuffer.putByte(nativeBuffer, i, (byte) charSequence.charAt(from + i));
        }
        return parse(nativeBuffer, length);
    }

    public void append(double value, StringBuilder builder) {
        int length = append(value, nativeBuffer);
        for (int i = 0; i < length; i++) {
            heapBuffer[i] = (char) DirectBuffer.getByte(nativeBuffer, i);
        }
        builder.append(heapBuffer, 0, length);
    }

    private static native double parse(long src, int srcLength);

    private static native int append(double value, long dest);
}
