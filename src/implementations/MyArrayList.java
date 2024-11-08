package implementations;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import utilities.Iterator;
import utilities.ListADT;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyArrayList<T> implements ListADT<T> {

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
        arrayList = (T[]) new Object[0];
    }

    @Override
    public boolean add(int index, T toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Object to be inserted is null");
        } if (index > this.size() || index < 0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index < this.size()) {
            T oldValue;
            T newValue = toAdd;
            this.extend();
            for (int j = index; j < size(); j++) {
                oldValue = arrayList[j];
                arrayList[j] = newValue;
                newValue = oldValue;
            }
        } if (index == this.size()) {
            this.extend();
            this.arrayList[index] = toAdd;
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
    public boolean add(T toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Object to be inserted is null");
        }
        int index = this.size();
        this.extend();
        this.arrayList[index] = toAdd;
        return true;
    }

    @Override
    public boolean addAll(ListADT toAdd) throws NullPointerException {
        if (toAdd.isEmpty()) {
            throw new NullPointerException("List to be inserted is null");
        }
        int oldLength = this.arrayList.length;
        this.extend(toAdd.size());
        for (int i = oldLength; i < oldLength+toAdd.size(); i++){
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

    private double get(T toFind){
        if (toFind == null) {
            throw new NullPointerException("Object to be inserted is null");
        }
        int index = 0;
        for (T item : arrayList) {
            if (item.equals(toFind)){
                return index;
            }
            index++;
        }
        return 0.1;
    };

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= this.arrayList.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T removedValue = arrayList[index];
        T[] newArray = (T[]) new Object[this.size()-1];
        for (int i = 0; i < this.arrayList.length; i++) {
            if(i < index && i != index) {
                newArray[i] = this.arrayList[i];
            }
            if (i > index && i != index) {
                newArray[i-1] = this.arrayList[i];
            }
        }
        arrayList = newArray;
        return removedValue;
    }

    @Override
    public T remove(T toRemove) throws NullPointerException {
        double check = get(toRemove);
        if ((check % 1) == 0) {
            return this.remove((int) this.get(toRemove));
        }
        return null;
    }

    @Override
    public T set(int index, T toChange) throws NullPointerException, IndexOutOfBoundsException {
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
    public T[] toArray(T[] toHold) throws NullPointerException {
        if (toHold == null){
            throw new NullPointerException("Object to hold is null");
        }
        if (toHold.length >= arrayList.length){
            System.arraycopy(arrayList, 0, toHold, 0, toHold.length);
        }
        if (toHold.length < arrayList.length){
            toHold = ((T[]) Array.newInstance(toHold.getClass().getComponentType(), arrayList.length));
            System.arraycopy(arrayList, 0, toHold, 0, toHold.length);
        }
        return toHold;
    }

    @Override
    public T[] toArray() {
        return arrayList;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator<T>(this.arrayList);
    }
}
