package eu.menzani.data;

import eu.menzani.object.Allocator;
import eu.menzani.object.PoolObject;

import java.util.*;
import java.util.function.Consumer;

public class Array extends Element implements Iterable<Element> {
    private static final Allocator<Array> allocator = Allocator.create(Array::new);

    private List<Element> elements;

    public static Array allocate() {
        Array instance = allocator.allocate();
        instance.clear();
        return instance;
    }

    private Array() {
        gc();
    }

    public void add(short value) {
        elements.add(Integer.allocate(value));
    }

    public void add(int value) {
        elements.add(Integer.allocate(value));
    }

    public void add(long value) {
        elements.add(Integer.allocate(value));
    }

    public void add(boolean value) {
        elements.add(Boolean.allocate(value));
    }

    public void add(float value) {
        elements.add(Decimal.allocate(value));
    }

    public void add(double value) {
        elements.add(Decimal.allocate(value));
    }

    public void add(java.lang.String value) {
        elements.add(String.allocate(value));
    }

    public void add(CharSequence value) {
        elements.add(String.allocate(value));
    }

    public void add(Element value) {
        elements.add(value);
    }

    public void addNull() {
        elements.add(null);
    }

    public void set(int index, short value) {
        elements.set(index, Integer.allocate(value));
    }

    public void set(int index, int value) {
        elements.set(index, Integer.allocate(value));
    }

    public void set(int index, long value) {
        elements.set(index, Integer.allocate(value));
    }

    public void set(int index, boolean value) {
        elements.set(index, Boolean.allocate(value));
    }

    public void set(int index, float value) {
        elements.set(index, Decimal.allocate(value));
    }

    public void set(int index, double value) {
        elements.set(index, Decimal.allocate(value));
    }

    public void set(int index, java.lang.String value) {
        elements.set(index, String.allocate(value));
    }

    public void set(int index, CharSequence value) {
        elements.set(index, String.allocate(value));
    }

    public void set(int index, Element value) {
        elements.set(index, value);
    }

    public void setNull(int index) {
        elements.set(index, null);
    }

    public boolean contains(short value) {
        return elements.contains(Integer.allocate(value));
    }

    public boolean contains(int value) {
        return elements.contains(Integer.allocate(value));
    }

    public boolean contains(long value) {
        return elements.contains(Integer.allocate(value));
    }

    public boolean contains(boolean value) {
        return elements.contains(Boolean.allocate(value));
    }

    public boolean contains(float value) {
        return elements.contains(Decimal.allocate(value));
    }

    public boolean contains(double value) {
        return elements.contains(Decimal.allocate(value));
    }

    public boolean contains(java.lang.String value) {
        return elements.contains(String.allocate(value));
    }

    public boolean contains(CharSequence value) {
        return elements.contains(String.allocate(value));
    }

    public boolean contains(Element value) {
        return elements.contains(value);
    }

    public boolean containsNull() {
        return elements.contains(null);
    }

    public short getShort(int index) {
        return ((Integer) elements.get(index)).asShort();
    }

    public int getInt(int index) {
        return ((Integer) elements.get(index)).asInt();
    }

    public long getLong(int index) {
        return ((Integer) elements.get(index)).asLong();
    }

    public boolean getJavaBoolean(int index) {
        return ((Boolean) elements.get(index)).asPrimitive();
    }

    public float getFloat(int index) {
        return ((Decimal) elements.get(index)).asFloat();
    }

    public double getDouble(int index) {
        return ((Decimal) elements.get(index)).asDouble();
    }

    public java.lang.String getJavaString(int index) {
        return ((String) elements.get(index)).asJavaString();
    }

    public CharSequence getCharSequence(int index) {
        return ((String) elements.get(index)).asCharSequence();
    }

    public Integer getInteger(int index) {
        return (Integer) elements.get(index);
    }

    public Boolean getBoolean(int index) {
        return (Boolean) elements.get(index);
    }

    public Decimal getDecimal(int index) {
        return (Decimal) elements.get(index);
    }

    public String getString(int index) {
        return (String) elements.get(index);
    }

    public Array getArray(int index) {
        return (Array) elements.get(index);
    }

    public Object getObject(int index) {
        return (Object) elements.get(index);
    }

    public Element getElement(int index) {
        return elements.get(index);
    }

    public void clear() {
        elements.clear();
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    @Override
    public void forEach(Consumer<? super Element> action) {
        elements.forEach(action);
    }

    @Override
    public Spliterator<Element> spliterator() {
        return elements.spliterator();
    }

    public List<Element> asList() {
        return elements;
    }

    public List<Element> asListView() {
        return Collections.unmodifiableList(asList());
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Array that = (Array) object;
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
        elements = new ArrayList<>();
    }

    @Override
    public void deallocate() {
        for (PoolObject element : elements) {
            if (element != null) {
                element.deallocate();
            }
        }
        allocator.deallocate(this);
    }
}
