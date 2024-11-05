package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

public class MyDLL2<T> implements ListADT<T>, Iterator<T> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() throws NoSuchElementException {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean add(int index, T toAdd) throws NullPointerException, IndexOutOfBoundsException {
        return false;
    }

    @Override
    public boolean add(T toAdd) throws NullPointerException {
        return false;
    }

    @Override
    public boolean addAll(ListADT<? extends T> toAdd) throws NullPointerException {
        return false;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public T remove(T toRemove) throws NullPointerException {
        return null;
    }

    @Override
    public T set(int index, T toChange) throws NullPointerException, IndexOutOfBoundsException {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(T toFind) throws NullPointerException {
        return false;
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
