package eu.menzani.lang;

public class StaticFieldOffset {
    private final Object base;
    private final long offset;

    StaticFieldOffset(Object base, long offset) {
        this.base = base;
        this.offset = offset;
    }

    public Object getBase() {
        return base;
    }

    public long getOffset() {
        return offset;
    }
}
