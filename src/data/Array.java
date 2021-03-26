package eu.menzani.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Array extends Element {
    private List<Element> elements = new ArrayList<>();

    public void setAsShort(int key, short value) {
        asInteger(key).set(value);
    }

    public void setAsInt(int key, int value) {
        asInteger(key).set(value);
    }

    public void setAsLong(int key, long value) {
        asInteger(key).set(value);
    }

    public void setAsFloat(int key, float value) {
        asDecimal(key).set(value);
    }

    public void setAsDouble(int key, double value) {
        asDecimal(key).set(value);
    }

    public StringBuilder setAsString(int key) {
        return asString(key).set();
    }

    public short getAsShort(int key) {
        return asInteger(key).asShort();
    }

    public int getAsInt(int key) {
        return asInteger(key).asInt();
    }

    public long getAsLong(int key) {
        return asInteger(key).asLong();
    }

    public float getAsFloat(int key) {
        return asDecimal(key).asFloat();
    }

    public double getAsDouble(int key) {
        return asDecimal(key).asDouble();
    }

    public java.lang.String getAsString(int key) {
        return asString(key).asJavaString();
    }

    public CharSequence getAsCharSequence(int key) {
        return asString(key).asCharSequence();
    }

    public Integer asInteger(int key) {
        return (Integer) asElement(key);
    }

    public Decimal asDecimal(int key) {
        return (Decimal) asElement(key);
    }

    public String asString(int key) {
        return (String) asElement(key);
    }

    public Object asObject(int key) {
        return (Object) asElement(key);
    }

    public Array asArray(int key) {
        return (Array) asElement(key);
    }

    public Element asElement(int key) {
        return elements.get(key);
    }

    public void clear() {
        elements.clear();
    }

    public List<Element> asList() {
        return elements;
    }

    public List<Element> asUnmodifiableList() {
        return Collections.unmodifiableList(asList());
    }

    @Override
    public void reconstruct() {
        clear();
    }

    @Override
    public void gc() {
        elements = new ArrayList<>();
    }
}
