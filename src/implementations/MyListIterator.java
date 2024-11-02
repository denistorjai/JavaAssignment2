package implementations;

import utilities.Iterator;

import java.util.NoSuchElementException;

public class MyListIterator implements Iterator {

    private final MyArrayList<Object> arrayList;
    private int index;
    public MyListIterator(MyArrayList<Object> insertedArrayList) {
        this.arrayList = insertedArrayList;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < arrayList.size();
    }

    @Override
    public Object next() throws NoSuchElementException {
        if (arrayList.get(index+1) == null) {
            throw new NoSuchElementException("Element is not in the list");
        }
        return arrayList.get(index+1);
    }
}
