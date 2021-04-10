package eu.menzani.data;

import eu.menzani.collection.Buffer;
import eu.menzani.collection.IterableIterator;
import eu.menzani.lang.Numbers;
import eu.menzani.lang.Optional;
import eu.menzani.object.Allocator;
import eu.menzani.struct.Iterables;

public class Object extends Element {
    private static final Allocator<Object> allocator = Allocator.create(Object::new);

    private KeyValue[] buckets = new KeyValue[16];
    private int mask = 15;
    private int growSize = 12;
    private int size;

    private KeyIterator keyIterator;
    private ValueIterator valueIterator;
    private KeyValueIterator keyValueIterator;
    private Buffer<KeyValue> buffer_usedByEquals;

    public static Object allocate() {
        return allocator.allocate();
    }

    public static Object allocate(int initialCapacity, float loadFactor) {
        Object instance = allocator.allocate();
        instance.ensureCapacity(initialCapacity);
        instance.growSize = (int) (instance.buckets.length * loadFactor);
        return instance;
    }

    private Object() {
    }

    public void ensureCapacity(int capacity) {
        if (!Numbers.isPowerOfTwo(capacity)) {
            throw new IllegalArgumentException("capacity must be a power of 2.");
        }
        int currentCapacity = buckets.length;
        if (currentCapacity < capacity) {
            growSize = capacity * (currentCapacity / growSize);
            resizeBuckets(capacity);
        }
    }

    public void set(java.lang.String key, short value) {
        set(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, int value) {
        set(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, long value) {
        set(key, Integer.allocate(value));
    }

    public void set(java.lang.String key, boolean value) {
        set(key, Boolean.allocate(value));
    }

    public void set(java.lang.String key, float value) {
        set(key, Decimal.allocate(value));
    }

    public void set(java.lang.String key, double value) {
        set(key, Decimal.allocate(value));
    }

    public void set(java.lang.String key, java.lang.String value) {
        set(key, String.allocate(value));
    }

    public void set(java.lang.String key, CharSequence value) {
        set(key, String.allocate(value));
    }

    public void set(java.lang.String key, Element value) {
        if (size++ == growSize) {
            growSize *= 2;
            resizeBuckets(buckets.length * 2);
        }

        int hash = key.hashCode() & mask;
        KeyValue bucket = buckets[hash];
        if (bucket == null) {
            buckets[hash] = KeyValue.allocate(key, value, null);
        } else {
            while (true) {
                if (bucket.key.equals(key)) {
                    bucket.value = value;
                    break;
                }
                if (bucket.next == null) {
                    break;
                }
                bucket = bucket.next;
            }
            bucket.next = KeyValue.allocate(key, value, bucket);
        }
    }

    private void resizeBuckets(int newCapacity) {
        KeyValue[] newBuckets = new KeyValue[newCapacity];
        int newMask = newCapacity - 1;
        for (KeyValue bucket : buckets) {
            while (bucket != null) {
                int hash = bucket.key.hashCode() & newMask;
                KeyValue newBucket = newBuckets[hash];
                if (newBucket == null) {
                    newBuckets[hash] = bucket;
                } else {
                    while (newBucket.next != null) {
                        newBucket = newBucket.next;
                    }
                    newBucket.next = bucket;
                }
                bucket = bucket.next;
            }
        }
        buckets = newBuckets;
        mask = newMask;
        if (keyIterator != null) {
            keyIterator.setBuckets(newBuckets);
        }
        if (valueIterator != null) {
            valueIterator.setBuckets(newBuckets);
        }
        if (keyValueIterator != null) {
            keyValueIterator.setBuckets(newBuckets);
        }
    }

    public void setNull(java.lang.String key) {
        set(key, (Element) null);
    }

    public boolean remove(java.lang.String key) {
        int hash = key.hashCode() & mask;
        KeyValue bucket = buckets[hash];
        boolean first = true;
        while (true) {
            if (bucket == null) {
                return false;
            }
            if (bucket.key.equals(key)) {
                break;
            }
            bucket = bucket.next;
            first = false;
        }
        if (first) {
            buckets[hash] = null;
        } else if (bucket.previous != null) {
            bucket.previous.next = bucket.next;
        }
        if (bucket.next != null) {
            bucket.next.previous = bucket.previous;
        }
        bucket.deallocate();
        size--;
        return true;
    }

    public boolean containsKey(java.lang.String key) {
        KeyValue bucket = null;
        int index = 0;
        while (true) {
            if (bucket == null || bucket.next == null) {
                do {
                    if (index == buckets.length) {
                        return false;
                    }
                    bucket = buckets[index++];
                } while (bucket == null);
            } else {
                bucket = bucket.next;
            }
            if (key.equals(bucket.key)) {
                return true;
            }
        }
    }

    public boolean containsValue(Element value) {
        KeyValue bucket = null;
        int index = 0;
        while (true) {
            if (bucket == null || bucket.next == null) {
                do {
                    if (index == buckets.length) {
                        return false;
                    }
                    bucket = buckets[index++];
                } while (bucket == null);
            } else {
                bucket = bucket.next;
            }
            if (value.equals(bucket.value)) {
                return true;
            }
        }
    }

    public boolean containsNullValue() {
        KeyValue bucket = null;
        int index = 0;
        while (true) {
            if (bucket == null || bucket.next == null) {
                do {
                    if (index == buckets.length) {
                        return false;
                    }
                    bucket = buckets[index++];
                } while (bucket == null);
            } else {
                bucket = bucket.next;
            }
            if (bucket.value == null) {
                return true;
            }
        }
    }

    public boolean containsNullableValue(@Optional Element value) {
        if (value == null) {
            return containsNullValue();
        }
        return containsValue(value);
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
        Integer integer = getInteger(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asShort();
    }

    public int getInt(java.lang.String key, int defaultValue) {
        Integer integer = getInteger(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asInt();
    }

    public long getLong(java.lang.String key, long defaultValue) {
        Integer integer = getInteger(key);
        if (integer == null) {
            return defaultValue;
        }
        return integer.asLong();
    }

    public boolean getJavaBoolean(java.lang.String key, boolean defaultValue) {
        Boolean booleanElement = getBoolean(key);
        if (booleanElement == null) {
            return defaultValue;
        }
        return booleanElement.asPrimitive();
    }

    public float getFloat(java.lang.String key, float defaultValue) {
        Decimal decimal = getDecimal(key);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asFloat();
    }

    public double getDouble(java.lang.String key, double defaultValue) {
        Decimal decimal = getDecimal(key);
        if (decimal == null) {
            return defaultValue;
        }
        return decimal.asDouble();
    }

    public java.lang.String getJavaString(java.lang.String key, java.lang.String defaultValue) {
        String string = getString(key);
        if (string == null) {
            return defaultValue;
        }
        return string.asJavaString();
    }

    public CharSequence getCharSequence(java.lang.String key, CharSequence defaultValue) {
        String string = getString(key);
        if (string == null) {
            return defaultValue;
        }
        return string.asCharSequence();
    }

    public Integer getInteger(java.lang.String key, Integer defaultValue) {
        return (Integer) getElement(key, defaultValue);
    }

    public Boolean getBoolean(java.lang.String key, Boolean defaultValue) {
        return (Boolean) getElement(key, defaultValue);
    }

    public Decimal getDecimal(java.lang.String key, Decimal defaultValue) {
        return (Decimal) getElement(key, defaultValue);
    }

    public String getString(java.lang.String key, String defaultValue) {
        return (String) getElement(key, defaultValue);
    }

    public Array getArray(java.lang.String key, Array defaultValue) {
        return (Array) getElement(key, defaultValue);
    }

    public Object getObject(java.lang.String key, Object defaultValue) {
        return (Object) getElement(key, defaultValue);
    }

    public Element getElement(java.lang.String key, Element defaultValue) {
        KeyValue bucket = buckets[key.hashCode() & mask];
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return defaultValue;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            KeyValue bucket = buckets[i];
            while (bucket != null) {
                bucket.deallocate();
                bucket = bucket.next;
            }
            buckets[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isNotEmpty() {
        return size != 0;
    }

    public Iterable<java.lang.String> getKeys() {
        if (keyIterator == null) {
            keyIterator = new KeyIterator(buckets);
        }
        keyIterator.initialize();
        return keyIterator;
    }

    public Iterable<Element> getValues() {
        if (valueIterator == null) {
            valueIterator = new ValueIterator(buckets);
        }
        valueIterator.initialize();
        return valueIterator;
    }

    public Iterable<KeyValue> getKeyValues() {
        if (keyValueIterator == null) {
            keyValueIterator = new KeyValueIterator(buckets);
        }
        keyValueIterator.initialize();
        return keyValueIterator;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Object that = (Object) object;
        int size = size();
        if (size != that.size()) {
            return false;
        }
        buffer_usedByEquals = Buffer.createOrClearAndAddAll(buffer_usedByEquals, size, that.getKeyValues());
        for (KeyValue keyValue : getKeyValues()) {
            if (!buffer_usedByEquals.remove(keyValue)) {
                return false;
            }
        }
        assert buffer_usedByEquals.isEmpty();
        return true;
    }

    @Override
    public int hashCode() {
        return Iterables.orderIndependentHashCode(getKeyValues());
    }

    @Override
    public java.lang.String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        for (java.util.Iterator<KeyValue> iterator = getKeyValues().iterator(); ; ) {
            KeyValue keyValue = iterator.next();
            builder.append(keyValue.getKey());
            builder.append('=');
            builder.append(keyValue.getValue());
            if (iterator.hasNext()) {
                builder.append(", ");
            } else {
                builder.append('}');
                return builder.toString();
            }
        }
    }

    @Override
    public void gc() {
        if (buffer_usedByEquals != null) {
            buffer_usedByEquals.gc();
        }

        int newCapacity = size() + 16;
        growSize = newCapacity * (buckets.length / growSize);
        resizeBuckets(newCapacity);
    }

    @Override
    public void deallocate() {
        clear();
        allocator.deallocate(this);
    }

    private static abstract class AbstractIterator {
        private KeyValue[] buckets;

        private int index;
        KeyValue bucket;

        AbstractIterator(KeyValue[] buckets) {
            this.buckets = buckets;
        }

        void setBuckets(KeyValue[] buckets) {
            this.buckets = buckets;
        }

        void initialize() {
            index = 0;
            bucket = null;
        }

        public boolean hasNext() {
            if (bucket == null || bucket.next == null) {
                do {
                    int index = this.index++;
                    if (index == buckets.length) {
                        return false;
                    }
                    bucket = buckets[index];
                } while (bucket == null);
            } else {
                bucket = bucket.next;
            }
            return true;
        }
    }

    private static class KeyIterator extends AbstractIterator implements IterableIterator<java.lang.String> {
        KeyIterator(KeyValue[] buckets) {
            super(buckets);
        }

        @Override
        public java.lang.String next() {
            return bucket.key;
        }
    }

    private static class ValueIterator extends AbstractIterator implements IterableIterator<Element> {
        ValueIterator(KeyValue[] buckets) {
            super(buckets);
        }

        @Override
        public Element next() {
            return bucket.value;
        }
    }

    private static class KeyValueIterator extends AbstractIterator implements IterableIterator<KeyValue> {
        KeyValueIterator(KeyValue[] buckets) {
            super(buckets);
        }

        @Override
        public KeyValue next() {
            return bucket;
        }
    }
}
