package eu.menzani.data;

import eu.menzani.object.Allocator;
import eu.menzani.object.PoolObject;

import java.util.Objects;

public class KeyValue implements PoolObject {
    private static final Allocator<KeyValue> allocator = Allocator.create(KeyValue::new);

    java.lang.String key;
    Element value;
    KeyValue previous;
    KeyValue next;

    static KeyValue allocate(java.lang.String key, Element value, KeyValue previous) {
        KeyValue instance = allocator.allocate();
        instance.key = key;
        instance.value = value;
        instance.previous = previous;
        instance.next = null;
        return instance;
    }

    private KeyValue() {
    }

    public java.lang.String getKey() {
        return key;
    }

    public Element getValue() {
        return value;
    }

    public void setValue(Element value) {
        this.value = value;
    }

    @Override
    public boolean equals(java.lang.Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        KeyValue that = (KeyValue) object;
        if (!key.equals(that.key)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = 31 * key.hashCode();
        if (value == null) {
            return result;
        }
        return result + value.hashCode();
    }

    @Override
    public java.lang.String toString() {
        return key + '=' + value;
    }

    @Override
    public void gc() {
    }

    @Override
    public void deallocate() {
        allocator.deallocate(this);
    }
}
