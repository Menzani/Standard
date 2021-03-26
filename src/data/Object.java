package eu.menzani.data;

import java.util.*;

public class Object extends Element {
    private Map<java.lang.String, Element> elements = new HashMap<>();

    public void setAsShort(java.lang.String key, short value) {
        asInteger(key).set(value);
    }

    public void setAsInt(java.lang.String key, int value) {
        asInteger(key).set(value);
    }

    public void setAsLong(java.lang.String key, long value) {
        asInteger(key).set(value);
    }

    public void setAsFloat(java.lang.String key, float value) {
        asDecimal(key).set(value);
    }

    public void setAsDouble(java.lang.String key, double value) {
        asDecimal(key).set(value);
    }

    public StringBuilder setAsString(java.lang.String key) {
        return asString(key).set();
    }

    public short getAsShort(java.lang.String key) {
        return asInteger(key).asShort();
    }

    public int getAsInt(java.lang.String key) {
        return asInteger(key).asInt();
    }

    public long getAsLong(java.lang.String key) {
        return asInteger(key).asLong();
    }

    public float getAsFloat(java.lang.String key) {
        return asDecimal(key).asFloat();
    }

    public double getAsDouble(java.lang.String key) {
        return asDecimal(key).asDouble();
    }

    public java.lang.String getAsString(java.lang.String key) {
        return asString(key).asJavaString();
    }

    public CharSequence getAsCharSequence(java.lang.String key) {
        return asString(key).asCharSequence();
    }

    public Integer asInteger(java.lang.String key) {
        return (Integer) asElement(key);
    }

    public Decimal asDecimal(java.lang.String key) {
        return (Decimal) asElement(key);
    }

    public String asString(java.lang.String key) {
        return (String) asElement(key);
    }

    public Object asObject(java.lang.String key) {
        return (Object) asElement(key);
    }

    public Array asArray(java.lang.String key) {
        return (Array) asElement(key);
    }

    public Element asElement(java.lang.String key) {
        return elements.get(key);
    }

    public void clear() {
        elements.clear();
    }

    public Set<java.lang.String> getKeys() {
        return elements.keySet();
    }

    public Set<java.lang.String> getUnmodifiableKeys() {
        return Collections.unmodifiableSet(getKeys());
    }

    public Collection<Element> getValues() {
        return elements.values();
    }

    public Collection<Element> getUnmodifiableValues() {
        return Collections.unmodifiableCollection(getValues());
    }

    public Set<Map.Entry<java.lang.String, Element>> getKeysWithValues() {
        return elements.entrySet();
    }

    public Set<Map.Entry<java.lang.String, Element>> getUnmodifiableKeysWithValues() {
        return Collections.unmodifiableSet(getKeysWithValues());
    }

    public Map<java.lang.String, Element> asMap() {
        return elements;
    }

    public Map<java.lang.String, Element> asUnmodifiableMap() {
        return Collections.unmodifiableMap(asMap());
    }

    @Override
    public void reconstruct() {
        clear();
    }

    @Override
    public void gc() {
        elements = new HashMap<>();
    }
}
