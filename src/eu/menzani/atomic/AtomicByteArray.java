package eu.menzani.atomic;

import eu.menzani.system.Unsafe;

public class AtomicByteArray {
    public static long elementOffset(int index) {
        return Unsafe.ARRAY_BYTE_BASE_OFFSET + Unsafe.ARRAY_BYTE_INDEX_SCALE * index;
    }
}
