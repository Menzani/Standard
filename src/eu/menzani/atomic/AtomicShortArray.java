package eu.menzani.atomic;

import eu.menzani.system.Unsafe;

public class AtomicShortArray {
    public static long elementOffset(int index) {
        return Unsafe.ARRAY_SHORT_BASE_OFFSET + Unsafe.ARRAY_SHORT_INDEX_SCALE * index;
    }
}
