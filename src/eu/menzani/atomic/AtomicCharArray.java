package eu.menzani.atomic;

import eu.menzani.system.Unsafe;

public class AtomicCharArray {
    public static long elementOffset(int index) {
        return Unsafe.ARRAY_CHAR_BASE_OFFSET + Unsafe.ARRAY_CHAR_INDEX_SCALE * index;
    }
}
