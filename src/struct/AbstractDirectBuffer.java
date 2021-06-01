package eu.menzani.struct;

public interface AbstractDirectBuffer {
    long getCapacity();

    byte readByte(long offset);

    char readChar(long offset);

    short readShort(long offset);

    int readInt(long offset);

    long readLong(long offset);

    boolean readBoolean(long offset);

    float readFloat(long offset);

    double readDouble(long offset);

    void writeByte(long offset, byte value);

    void writeChar(long offset, char value);

    void writeShort(long offset, short value);

    void writeInt(long offset, int value);

    void writeLong(long offset, long value);

    void writeBoolean(long offset, boolean value);

    void writeFloat(long offset, float value);

    void writeDouble(long offset, double value);
}
