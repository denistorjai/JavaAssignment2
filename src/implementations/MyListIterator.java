package implementations;

import utilities.Iterator;

import java.util.NoSuchElementException;

public class MyListIterator<T> implements Iterator<T> {

    private final T[] arrayList;
    private int index;
    public MyListIterator(T[] insertedArrayList) {
        this.arrayList = insertedArrayList;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < arrayList.length;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return arrayList[index++];
    }
}
