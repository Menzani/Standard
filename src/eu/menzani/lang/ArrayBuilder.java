package eu.menzani.lang;

import eu.menzani.lang.mutable.BooleanRef;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ArrayBuilder<T> {
    private final List<T> list;
    private final Class<?> elementType;

    public ArrayBuilder(Class<T> elementType) {
        list = new ArrayList<>();
        this.elementType = elementType;
    }

    public ArrayBuilder(int capacity, Class<T> elementType) {
        list = new ArrayList<>(capacity);
        this.elementType = elementType;
    }

    public ArrayBuilder(T[] array) {
        list = new ArrayList<>(Arrays.asList(array));
        elementType = array.getClass().getComponentType();
    }

    public void add(T element) {
        list.add(element);
    }

    public void map(Mapper<T> mapper) {
        ListIterator<T> iterator = list.listIterator();
        BooleanRef shouldRemove = new BooleanRef();
        while (iterator.hasNext()) {
            shouldRemove.value = false;
            T newElement = mapper.map(iterator.next(), shouldRemove);
            if (shouldRemove.value) {
                iterator.remove();
            } else {
                iterator.set(newElement);
            }
        }
    }

    public T[] build() {
        @SuppressWarnings("unchecked") T[] dummy = (T[]) Array.newInstance(elementType, 0);
        return list.toArray(dummy);
    }

    public interface Mapper<E> {
        E map(E element, BooleanRef shouldRemove);
    }
}
