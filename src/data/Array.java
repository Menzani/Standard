package eu.menzani.data;

import eu.menzani.collection.ArrayView;
import eu.menzani.lang.Optional;
import eu.menzani.object.Allocator;
import eu.menzani.struct.Arrays;

import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class Array extends Element implements Iterable<Element> {
    private static final Allocator<Array> allocator = Allocator.create(Array::new);

    private Element[] elements = new Element[10];
    private int length;

    private Iterator iterator;

    public static Array allocate() {
        return allocator.allocate();
    }

    public static Array allocate(int initialCapacity) {
        Array instance = allocator.allocate();
        instance.ensureCapacity(initialCapacity);
        return instance;
    }

    private Array() {
    }

    public void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            resizeElements(capacity);
        }
    }

    public void add(short value) {
        add(Integer.allocate(value));
    }

    public void add(int value) {
        add(Integer.allocate(value));
    }

    public void add(long value) {
        add(Integer.allocate(value));
    }

    public void add(boolean value) {
        add(Boolean.allocate(value));
    }

    public void add(float value) {
        add(Decimal.allocate(value));
    }

    public void add(double value) {
        add(Decimal.allocate(value));
    }

    public void add(java.lang.String value) {
        add(String.allocate(value));
    }

    public void add(CharSequence value) {
        add(String.allocate(value));
    }

    public void add(Element value) {
        int length = this.length++;
        if (length == elements.length) {
            resizeElements(length * 2);
        }
        elements[length] = value;
    }

    private void resizeElements(int newCapacity) {
        elements = java.util.Arrays.copyOf(elements, newCapacity, Element[].class);
    }

    public void addNull() {
        add((Element) null);
    }

    public void remove(int index) {
        Element element = elements[index];
        if (element != null) {
            element.deallocate();
        }
        System.arraycopy(elements, index + 1, elements, index, (length--) - index);
    }

    public void set(int index, short value) {
        set(index, Integer.allocate(value));
    }

    public void set(int index, int value) {
        set(index, Integer.allocate(value));
    }

    public void set(int index, long value) {
        set(index, Integer.allocate(value));
    }

    public void set(int index, boolean value) {
        set(index, Boolean.allocate(value));
    }

    public void set(int index, float value) {
        set(index, Decimal.allocate(value));
    }

    public void set(int index, double value) {
        set(index, Decimal.allocate(value));
    }

    public void set(int index, java.lang.String value) {
        set(index, String.allocate(value));
    }

    public void set(int index, CharSequence value) {
        set(index, String.allocate(value));
    }

    public void set(int index, Element value) {
        elements[index] = value;
    }

    public void setNull(int index) {
        set(index, (Element) null);
    }

    public boolean contains(short value) {
        return contains(Integer.allocate(value));
    }

    public boolean contains(int value) {
        return contains(Integer.allocate(value));
    }

    public boolean contains(long value) {
        return contains(Integer.allocate(value));
    }

    public boolean contains(boolean value) {
        return contains(Boolean.allocate(value));
    }

    public boolean contains(float value) {
        return contains(Decimal.allocate(value));
    }

    public boolean contains(double value) {
        return contains(Decimal.allocate(value));
    }

    public boolean contains(java.lang.String value) {
        return contains(String.allocate(value));
    }

    public boolean contains(CharSequence value) {
        return contains(String.allocate(value));
    }

    public boolean contains(Element value) {
        for (int i = 0; i < length; i++) {
            if (value.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean containsNull() {
        for (int i = 0; i < length; i++) {
            if (elements[i] == null) {
                return true;
            }
        }
        return false;
    }

    public boolean containsNullable(@Optional Element value) {
        if (value == null) {
            return containsNull();
        }
        return contains(value);
    }

    public short getShort(int index) {
        return getInteger(index).asShort();
    }

    public int getInt(int index) {
        return getInteger(index).asInt();
    }

    public long getLong(int index) {
        return getInteger(index).asLong();
    }

    public boolean getJavaBoolean(int index) {
        return getBoolean(index).asPrimitive();
    }

    public float getFloat(int index) {
        return getDecimal(index).asFloat();
    }

    public double getDouble(int index) {
        return getDecimal(index).asDouble();
    }

    public java.lang.String getJavaString(int index) {
        return getString(index).asJavaString();
    }

    public CharSequence getCharSequence(int index) {
        return getString(index).asCharSequence();
    }

    public Integer getInteger(int index) {
        return (Integer) getElement(index);
    }

    public Boolean getBoolean(int index) {
        return (Boolean) getElement(index);
    }

    public Decimal getDecimal(int index) {
        return (Decimal) getElement(index);
    }

    public String getString(int index) {
        return (String) getElement(index);
    }

    public Array getArray(int index) {
        return (Array) getElement(index);
    }

    public Object getObject(int index) {
        return (Object) getElement(index);
    }

    public Element getElement(int index) {
        return elements[index];
    }

    public void clear() {
        for (int i = 0; i < length; i++) {
            Element element = elements[i];
            if (element != null) {
                element.deallocate();
                elements[i] = null;
            }
        }
        length = 0;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public boolean isNotEmpty() {
        return length != 0;
    }

    @Override
    public java.util.Iterator<Element> iterator() {
        return listIterator();
    }

    public ListIterator<Element> listIterator() {
        return listIterator(0, length);
    }

    public ListIterator<Element> listIterator(int from, int to) {
        if (iterator == null) {
            iterator = new Iterator();
        }
        iterator.initialize(from, to);
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super Element> action) {
        for (int i = 0; i < length; i++) {
            action.accept(elements[i]);
        }
    }

    @Override
    public Spliterator<Element> spliterator() {
        return Spliterators.spliterator(elements, 0, length, 0);
    }

    public List<Element> asListView() {
        return new ArrayView<>(elements, 0, length);
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Array that = (Array) object;
        int length = this.length;
        if (length != that.length) return false;
        return java.util.Arrays.equals(elements, 0, length, that.elements, 0, length);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements, 0, length);
    }

    @Override
    public java.lang.String toString() {
        return Arrays.toString(elements, 0, length);
    }

    @Override
    public void gc() {
        resizeElements(length + 10);
    }

    @Override
    public void deallocate() {
        clear();
        allocator.deallocate(this);
    }

    private class Iterator implements ListIterator<Element> {
        private int start;
        private int end;
        private int index;

        Iterator() {
        }

        void initialize(int start, int end) {
            this.start = start;
            this.end = end;
            index = start;
        }

        @Override
        public boolean hasNext() {
            return index != end;
        }

        @Override
        public Element next() {
            return elements[index++];
        }

        @Override
        public boolean hasPrevious() {
            return index != start;
        }

        @Override
        public Element previous() {
            return elements[--index];
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            Array.this.remove(index - 1);
        }

        @Override
        public void set(Element element) {
            elements[index] = element;
        }

        @Override
        public void add(Element element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Element> action) {
            for (int i = index; i < end; i++) {
                action.accept(elements[i]);
            }
            index = end;
        }
    }
}
