package eu.menzani.data;

import eu.menzani.object.PoolObject;

public abstract class Element implements PoolObject {
    public void set(Path path, short value) {
        integer(path).set(value);
    }

    public void set(Path path, int value) {
        integer(path).set(value);
    }

    public void set(Path path, long value) {
        integer(path).set(value);
    }

    public void set(Path path, float value) {
        decimal(path).set(value);
    }

    public void set(Path path, double value) {
        decimal(path).set(value);
    }

    public StringBuilder setString(Path path) {
        return string(path).set();
    }

    public short getShort(Path path) {
        return integer(path).asShort();
    }

    public int getInt(Path path) {
        return integer(path).asInt();
    }

    public long getLong(Path path) {
        return integer(path).asLong();
    }

    public float getFloat(Path path) {
        return decimal(path).asFloat();
    }

    public double getDouble(Path path) {
        return decimal(path).asDouble();
    }

    public java.lang.String getString(Path path) {
        return string(path).asJavaString();
    }

    public CharSequence getCharSequence(Path path) {
        return string(path).asCharSequence();
    }

    public Integer integer(Path path) {
        return (Integer) element(path);
    }

    public Decimal decimal(Path path) {
        return (Decimal) element(path);
    }

    public String string(Path path) {
        return (String) element(path);
    }

    public Object object(Path path) {
        return (Object) element(path);
    }

    public Array array(Path path) {
        return (Array) element(path);
    }

    public Element element(Path path) {
        java.lang.String[] strings = path.getStrings();
        int[] ints = path.getInts();

        Element result = this;
        for (int i = 0; i < strings.length; i++) {
            if (Path.isVoid(strings[i])) {
                result = ((Array) result).asElement(ints[i]);
            } else {
                assert Path.isVoid(ints[i]);
                result = ((Object) result).asElement(strings[i]);
            }
        }
        return result;
    }
}
