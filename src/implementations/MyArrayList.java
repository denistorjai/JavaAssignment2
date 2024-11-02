package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.ArrayList;

public class MyArrayList<T> implements ListADT {

    private T[] arrayList;

    public MyArrayList(T[] array){
        this.arrayList = array;
    }

    public MyArrayList(){
        this.arrayList = (T[]) new Object[0];
    }
    @Override
    public int size() {
        return arrayList.length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arrayList.length; i++) {
            arrayList[i] = null;
        }
    }

    @Override
    public boolean add(int index, Object toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Object to be inserted is null");
        } if (index > this.size() || index < 0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index < this.size()) {
            T oldValue;
            this.extend();
            oldValue = arrayList[index];
            arrayList[index] = (T) toAdd;
            arrayList[index + 1] = oldValue;
        } if (index == this.size()) {
            this.extend();
            this.arrayList[index] = (T) toAdd;
        }
        return true;
    }

    private void extend() {
        T[] oldArray = this.arrayList.clone();
        this.arrayList = (T[]) new Object[this.size()+1];
        for (int i = 0; i <= oldArray.length-1; i++) {
            this.arrayList[i] = oldArray[i];
        }
    }

    private void extend(int size) {
        T[] oldArray = this.arrayList.clone();
        this.arrayList = (T[]) new Object[this.size()+size];
        for (int i = 0; i <= oldArray.length-1; i++) {
            this.arrayList[i] = oldArray[i];
        }
    }

    @Override
    public boolean add(Object toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Object to be inserted is null");
        }
        int index = this.size();
        this.extend();
        this.arrayList[index] = (T) toAdd;
        return true;
    }

    @Override
    public boolean addAll(ListADT toAdd) throws NullPointerException {
        if (toAdd.isEmpty()) {
            throw new NullPointerException("List to be inserted is null");
        }
        int oldLength = this.arrayList.length;
        this.extend(toAdd.size());
        for (int i = oldLength; i < oldLength+toAdd.size()-1; i++){
            this.arrayList[i] = (T) toAdd.get(i-oldLength);
        }
        return true;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= this.arrayList.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return arrayList[index];
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= this.arrayList.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T object = arrayList[index];
        arrayList[index] = null;
        return object;
    }

    @Override
    public T remove(Object toRemove) throws NullPointerException {
        int index = 0;
        for (T item : arrayList) {
            if (item.equals(toRemove)){
                arrayList[index] = null;
                return item;
            }
            index++;
        }
        return null;
    }

    @Override
    public T set(int index, Object toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null){
            throw new NullPointerException("Object to set is null");
        }
        if (index > arrayList.length-1 || index<0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T oldValue = arrayList[index];
        arrayList[index] = (T) toChange;
        return oldValue;
    }

    @Override
    public boolean isEmpty() {
        return arrayList.length == 0;
    };

    @Override
    public boolean contains(Object toFind) throws NullPointerException {
        if (toFind == null){
            throw new NullPointerException("Object to find is null");
        }
        for (T item : arrayList) {
            if (item.equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray(Object[] toHold) throws NullPointerException {
        return new Object[0];
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Iterator iterator() {
        return new MyListIterator((MyArrayList<Object>) this);
    }
}
