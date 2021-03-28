package eu.menzani.data;

import eu.menzani.object.Allocator;

import java.util.*;

public class Object extends Element {
    private static final Allocator<Object> allocator = Allocator.create(Object::new);

    private Map<java.lang.String, Element> elements;

    public static Object allocate() {
        Object instance = allocator.allocate();
        instance.clear();
        return instance;
    }

    private Object() {
        gc();
    }

    public void set(java.lang.String key, short value) {
        elements.put(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, int value) {
        elements.put(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, long value) {
        elements.put(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, boolean value) {
        elements.put(key, Boolean.allocate(value));
    }

    public void set(java.lang.String key, float value) {
        elements.put(key, Decimal.allocate(value));
    }

    public void set(java.lang.String key, double value) {
        elements.put(key, Decimal.allocate(value));
    }

    public void set(java.lang.String key, java.lang.String value) {
        elements.put(key, String.allocate(value));
    }

    public void set(java.lang.String key, CharSequence value) {
        elements.put(key, String.allocate(value));
    }

    public void set(java.lang.String key, Element value) {
        elements.put(key, value);
    }

    public short getShort(java.lang.String key) {
        return getShort(key, (short) 0);
    }

    public int getInt(java.lang.String key) {
        return getInt(key, 0);
    }

    public long getLong(java.lang.String key) {
        return getLong(key, 0L);
    }

    public boolean getJavaBoolean(java.lang.String key) {
        return getJavaBoolean(key, false);
    }

    public float getFloat(java.lang.String key) {
        return getFloat(key, 0F);
    }

    public double getDouble(java.lang.String key) {
        return getDouble(key, 0D);
    }

    public java.lang.String getJavaString(java.lang.String key) {
        return getJavaString(key, null);
    }

    public CharSequence getCharSequence(java.lang.String key) {
        return getCharSequence(key, null);
    }

    public Integer getInteger(java.lang.String key) {
        return getInteger(key, null);
    }

    public Boolean getBoolean(java.lang.String key) {
        return getBoolean(key, null);
    }

    public Decimal getDecimal(java.lang.String key) {
        return getDecimal(key, null);
    }

    public String getString(java.lang.String key) {
        return getString(key, null);
    }

    public Array getArray(java.lang.String key) {
        return getArray(key, null);
    }

    public Object getObject(java.lang.String key) {
        return getObject(key, null);
    }

    public Element getElement(java.lang.String key) {
        return getElement(key, null);
    }

    public short getShort(java.lang.String key, short defaultValue) {
        Integer integer = (Integer) elements.get(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asShort();
    }

    public int getInt(java.lang.String key, int defaultValue) {
        Integer integer = (Integer) elements.get(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asInt();
    }

    public long getLong(java.lang.String key, long defaultValue) {
        Integer integer = (Integer) elements.get(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asLong();
    }

    public boolean getJavaBoolean(java.lang.String key, boolean defaultValue) {
        Boolean booleanElement = (Boolean) elements.get(key);
        if (booleanElement == null) {
            return defaultValue;
        }
        return booleanElement.asPrimitive();
    }

    public float getFloat(java.lang.String key, float defaultValue) {
        Decimal decimal = (Decimal) elements.get(key);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asFloat();
    }

    public double getDouble(java.lang.String key, double defaultValue) {
        Decimal decimal = (Decimal) elements.get(key);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asDouble();
    }

    public java.lang.String getJavaString(java.lang.String key, java.lang.String defaultValue) {
        String string = (String) elements.get(key);
        if (string == null) {
            return defaultValue;
        }
        return string.asJavaString();
    }

    public CharSequence getCharSequence(java.lang.String key, CharSequence defaultValue) {
        String string = (String) elements.get(key);
        if (string == null) {
            return defaultValue;
        }
        return string.asCharSequence();
    }

    public Integer getInteger(java.lang.String key, Integer defaultValue) {
        return (Integer) elements.getOrDefault(key, defaultValue);
    }

    public Boolean getBoolean(java.lang.String key, Boolean defaultValue) {
        return (Boolean) elements.getOrDefault(key, defaultValue);
    }

    public Decimal getDecimal(java.lang.String key, Decimal defaultValue) {
        return (Decimal) elements.getOrDefault(key, defaultValue);
    }

    public String getString(java.lang.String key, String defaultValue) {
        return (String) elements.getOrDefault(key, defaultValue);
    }

    public Array getArray(java.lang.String key, Array defaultValue) {
        return (Array) elements.getOrDefault(key, defaultValue);
    }

    public Object getObject(java.lang.String key, Object defaultValue) {
        return (Object) elements.getOrDefault(key, defaultValue);
    }

    public Element getElement(java.lang.String key, Element defaultValue) {
        return elements.getOrDefault(key, defaultValue);
    }

    public void clear() {
        elements.clear();
    }

    public Set<java.lang.String> getKeys() {
        return elements.keySet();
    }

    public Set<java.lang.String> getKeysView() {
        return Collections.unmodifiableSet(getKeys());
    }

    public Collection<Element> getValues() {
        return elements.values();
    }

    public Collection<Element> getValuesView() {
        return Collections.unmodifiableCollection(getValues());
    }

    public Set<Map.Entry<java.lang.String, Element>> getKeysWithValues() {
        return elements.entrySet();
    }

    public Set<Map.Entry<java.lang.String, Element>> getKeysWithValuesView() {
        return Collections.unmodifiableSet(getKeysWithValues());
    }

    public Map<java.lang.String, Element> asMap() {
        return elements;
    }

    public Map<java.lang.String, Element> asMapView() {
        return Collections.unmodifiableMap(asMap());
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Object that = (Object) object;
        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public java.lang.String toString() {
        return elements.toString();
    }

    @Override
    public void gc() {
        elements = new HashMap<>();
    }

    @Override
    public void deallocate() {
        allocator.deallocate(this);
    }
}
