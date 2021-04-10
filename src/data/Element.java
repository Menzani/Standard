package eu.menzani.data;

import eu.menzani.lang.Optional;
import eu.menzani.object.PoolObject;

public abstract class Element implements PoolObject {
    Element() {
    }

    public void set(Path path, short value) {
        set(path, Integer.allocate(value));
    }

    public void set(Path path, int value) {
        set(path, Integer.allocate(value));
    }

    public void set(Path path, long value) {
        set(path, Integer.allocate(value));
    }

    public void set(Path path, boolean value) {
        set(path, Boolean.allocate(value));
    }

    public void set(Path path, float value) {
        set(path, Decimal.allocate(value));
    }

    public void set(Path path, double value) {
        set(path, Decimal.allocate(value));
    }

    public void set(Path path, java.lang.String value) {
        set(path, String.allocate(value));
    }

    public void set(Path path, CharSequence value) {
        set(path, String.allocate(value));
    }

    public void set(Path path, Element value) {
        java.lang.String[] keys = path.getKeys();
        Object current = (Object) this;
        for (int i = 0; ; ) {
            java.lang.String key = keys[i];
            if (++i == keys.length) {
                current.set(key, value);
                break;
            }
            Object result = current.getObject(key);
            if (result == null && !current.containsKey(key)) {
                result = Object.allocate();
                current.set(key, result);
            }
            current = result;
        }
    }

    public void setNull(Path path) {
        set(path, (Element) null);
    }

    public boolean remove(Path path) {
        java.lang.String[] keys = path.getKeys();
        Object current = (Object) this;
        for (int i = 0; ; ) {
            java.lang.String key = keys[i];
            if (++i == keys.length) {
                return current.remove(key);
            }
            Object result = current.getObject(key);
            if (result == null && !current.containsKey(key)) {
                return false;
            }
            current = result;
        }
    }

    public boolean containsKey(Path path) {
        java.lang.String[] keys = path.getKeys();
        Object current = (Object) this;
        for (int i = 0; ; ) {
            java.lang.String key = keys[i];
            if (++i == keys.length) {
                return current.containsKey(key);
            }
            current = current.getObject(key);
        }
    }

    public boolean containsValue(Path path, Element value) {
        Object current = (Object) this;
        for (java.lang.String key : path.getKeys()) {
            current = current.getObject(key);
        }
        return current.containsValue(value);
    }

    public boolean containsNullValue(Path path) {
        Object current = (Object) this;
        for (java.lang.String key : path.getKeys()) {
            current = current.getObject(key);
        }
        return current.containsNullValue();
    }

    public boolean containsNullableValue(Path path, @Optional Element value) {
        if (value == null) {
            return containsNullValue(path);
        }
        return containsValue(path, value);
    }

    public short getShort(Path path) {
        return getShort(path, (short) 0);
    }

    public int getInt(Path path) {
        return getInt(path, 0);
    }

    public long getLong(Path path) {
        return getLong(path, 0L);
    }

    public boolean getJavaBoolean(Path path) {
        return getJavaBoolean(path, false);
    }

    public float getFloat(Path path) {
        return getFloat(path, 0F);
    }

    public double getDouble(Path path) {
        return getDouble(path, 0D);
    }

    public java.lang.String getJavaString(Path path) {
        return getJavaString(path, null);
    }

    public CharSequence getCharSequence(Path path) {
        return getCharSequence(path, null);
    }

    public Integer getInteger(Path path) {
        return getInteger(path, null);
    }

    public Boolean getBoolean(Path path) {
        return getBoolean(path, null);
    }

    public Decimal getDecimal(Path path) {
        return getDecimal(path, null);
    }

    public String getString(Path path) {
        return getString(path, null);
    }

    public Array getArray(Path path) {
        return getArray(path, null);
    }

    public Object getObject(Path path) {
        return getObject(path, null);
    }

    public Element getElement(Path path) {
        return getElement(path, null);
    }

    public short getShort(Path path, short defaultValue) {
        Integer integer = getInteger(path);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asShort();
    }

    public int getInt(Path path, int defaultValue) {
        Integer integer = getInteger(path);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asInt();
    }

    public long getLong(Path path, long defaultValue) {
        Integer integer = getInteger(path);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asLong();
    }

    public boolean getJavaBoolean(Path path, boolean defaultValue) {
        Boolean booleanElement = getBoolean(path);
        if (booleanElement == null) {
            return defaultValue;
        }
        return booleanElement.asPrimitive();
    }

    public float getFloat(Path path, float defaultValue) {
        Decimal decimal = getDecimal(path);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asFloat();
    }

    public double getDouble(Path path, double defaultValue) {
        Decimal decimal = getDecimal(path);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asDouble();
    }

    public java.lang.String getJavaString(Path path, java.lang.String defaultValue) {
        String string = getString(path);
        if (string == null) {
            return defaultValue;
        }
        return string.asJavaString();
    }

    public CharSequence getCharSequence(Path path, CharSequence defaultValue) {
        String string = getString(path);
        if (string == null) {
            return defaultValue;
        }
        return string.asCharSequence();
    }

    public Integer getInteger(Path path, Integer defaultValue) {
        return (Integer) getElement(path, defaultValue);
    }

    public Boolean getBoolean(Path path, Boolean defaultValue) {
        return (Boolean) getElement(path, defaultValue);
    }

    public Decimal getDecimal(Path path, Decimal defaultValue) {
        return (Decimal) getElement(path, defaultValue);
    }

    public String getString(Path path, String defaultValue) {
        return (String) getElement(path, defaultValue);
    }

    public Array getArray(Path path, Array defaultValue) {
        return (Array) getElement(path, defaultValue);
    }

    public Object getObject(Path path, Object defaultValue) {
        return (Object) getElement(path, defaultValue);
    }

    public Element getElement(Path path, Element defaultValue) {
        Element current = this;
        for (java.lang.String key : path.getKeys()) {
            Object currentObject = (Object) current;
            Element result = currentObject.getElement(key);
            if (result == null && !currentObject.containsKey(key)) {
                return defaultValue;
            }
            current = result;
        }
        return current;
    }
}
