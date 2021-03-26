package eu.menzani.data;

enum NumberSize {
    SHORT(0),
    INT(1),
    LONG(2),

    FLOAT(3),
    DOUBLE(4);

    private final int id;

    NumberSize(int id) {
        this.id = id;
    }

    boolean contains(NumberSize other) {
        return other.id <= id;
    }
}
