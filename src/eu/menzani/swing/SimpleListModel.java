package eu.menzani.swing;

import eu.menzani.lang.Assume;

import javax.swing.*;
import java.util.*;

public class SimpleListModel<E> extends AbstractListModel<E> implements List<E> {
    private final ArrayList<E> delegate = new ArrayList<>();

    @Override
    public int getSize() {
        return delegate.size();
    }

    @Override
    public E getElementAt(int index) {
        return delegate.get(index);
    }

    public void copyInto(Object[] anArray) {
        if (anArray.length < delegate.size()) {
            throw new IndexOutOfBoundsException();
        }
        delegate.toArray(anArray);
    }

    public void trimToSize() {
        delegate.trimToSize();
    }

    public void ensureCapacity(int minCapacity) {
        delegate.ensureCapacity(minCapacity);
    }

    public void setSize(int newSize) {
        int oldSize = delegate.size();
        if (oldSize > newSize) {
            delegate.subList(newSize, oldSize).clear();
            fireIntervalRemoved(this, newSize, oldSize - 1);
        } else if (oldSize < newSize) {
            delegate.addAll(Collections.nCopies(newSize - oldSize, null));
            fireIntervalAdded(this, oldSize, newSize - 1);
        }
    }

    public int capacity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public Enumeration<E> elements() {
        return Collections.enumeration(delegate);
    }

    @Override
    public boolean contains(Object elem) {
        return delegate.contains(elem);
    }

    @Override
    public int indexOf(Object elem) {
        return delegate.indexOf(elem);
    }

    public int indexOf(Object elem, int index) {
        for (; index < delegate.size(); index++) {
            if (elem.equals(delegate.get(index))) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object elem) {
        return delegate.lastIndexOf(elem);
    }

    public int lastIndexOf(Object elem, int index) {
        for (; index >= 0; index--) {
            if (elem.equals(delegate.get(index))) {
                return index;
            }
        }
        return -1;
    }

    public E elementAt(int index) {
        return delegate.get(index);
    }

    public E firstElement() {
        return delegate.get(0);
    }

    public E lastElement() {
        return delegate.get(delegate.size() - 1);
    }

    public void setElementAt(E element, int index) {
        set(index, element);
    }

    public void removeElementAt(int index) {
        remove(index);
    }

    public void insertElementAt(E element, int index) {
        add(index, element);
    }

    public void addElement(E element) {
        add(element);
    }

    public boolean removeElement(Object obj) {
        return remove(obj);
    }

    public void removeAllElements() {
        clear();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public E get(int index) {
        return delegate.get(index);
    }

    @Override
    public E set(int index, E element) {
        E rv = delegate.set(index, element);
        fireContentsChanged(this, index, index);
        return rv;
    }

    @Override
    public void add(int index, E element) {
        delegate.add(index, element);
        fireIntervalAdded(this, index, index);
    }

    @Override
    public E remove(int index) {
        E rv = delegate.remove(index);
        fireIntervalRemoved(this, index, index);
        return rv;
    }

    @Override
    public void clear() {
        int index1 = delegate.size() - 1;
        delegate.clear();
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }

    public void removeRange(int fromIndex, int toIndex) {
        Assume.notGreaterThan(fromIndex, toIndex);
        delegate.subList(fromIndex, toIndex + 1).clear();
        fireIntervalRemoved(this, fromIndex, toIndex);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        int startIndex = delegate.size();
        delegate.addAll(c);
        fireIntervalAdded(this, startIndex, delegate.size() - 1);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > delegate.size()) {
            throw new ArrayIndexOutOfBoundsException("index out of range: " + index);
        }
        if (c.isEmpty()) {
            return false;
        }
        delegate.addAll(index, c);
        fireIntervalAdded(this, index, index + c.size() - 1);
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        int index = delegate.size();
        delegate.add(e);
        fireIntervalAdded(this, index, index);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            delegate.remove(index);
            fireIntervalRemoved(this, index, index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            changed |= remove(o);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        ListIterator<E> iterator = delegate.listIterator();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                int index = iterator.previousIndex();
                fireIntervalRemoved(this, index, index);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public ListIterator<E> listIterator() {
        return delegate.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return delegate.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }
}
