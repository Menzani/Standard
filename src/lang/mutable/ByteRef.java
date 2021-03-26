package eu.menzani.lang.mutable;

public class ByteRef {
    public byte value;

    @Override
    public String toString() {
        return Byte.toString(value);
    }
}
