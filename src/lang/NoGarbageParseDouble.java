package eu.menzani.lang;

import eu.menzani.Native;
import eu.menzani.struct.DirectBuffer;
import eu.menzani.system.Garbage;
import eu.menzani.system.Unsafe;

public class NoGarbageParseDouble {
    static {
        Native.init();
    }

    private final long buffer = Unsafe.allocateMemory(21L);
    private int length;

    public NoGarbageParseDouble() {
        Garbage.freeMemory(this, buffer);
    }

    public void reset() {
        length = 0;
    }

    public void put(CharSequence charSequence) {
        put(charSequence, 0, charSequence.length());
    }

    public void put(CharSequence charSequence, int from, int to) {
        for (; from < to; from++) {
            put(charSequence.charAt(from));
        }
    }

    public void put(char[] buffer, int from, int to) {
        for (; from < to; from++) {
            put(buffer[from]);
        }
    }

    public void put(char character) {
        DirectBuffer.putByte(buffer, length++, (byte) character);
    }

    public double parse() {
        return parse(buffer, length);
    }

    public static native double parse(long src, int srcLength);
}
