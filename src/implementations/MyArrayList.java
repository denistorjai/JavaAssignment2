package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.lang.reflect.Array;

public class MyArrayList<T> implements ListADT<T> {

    private T[] arrayList;


    /**
     * Constructor for MyArrayList class
     * creates an empty list on call
     */
    public MyArrayList(){
        this.arrayList = (T[]) new Object[0];
    }


    /**
     * Getter for list's size
     * @return - current size of the list
     */
    @Override
    public int size() {
        return arrayList.length;
    }


    /**
     * Method to clear the list
     * Overwrites the current list with a new one
     */
    @Override
    public void clear() {
        arrayList = (T[]) new Object[0];
    }


    /**
     *
     * @param index The index at which the specified element is to be inserted. The
     *              element is inserted before the existing element at [index], or
     *              at the end if index is equal to the size (<code>size()</code>).
     * @param toAdd The element to be inserted.
     * @return - True if item was added successfully, false otherwise
     * @throws NullPointerException - If object to add == null
     * @throws IndexOutOfBoundsException - If index more than size of the list or less than 0
     */
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


    /**
     * Extends the list by one.
     * 1) Creates a new list
     * 2) Copies the old one
     * 3) Inserts old values into a new list
     */
    private void extend() {
        T[] oldArray = this.arrayList.clone();
        this.arrayList = (T[]) new Object[this.size()+1];
        for (int i = 0; i <= oldArray.length-1; i++) {
            this.arrayList[i] = oldArray[i];
        }
    }

    /**
     * Extends the list by one.
     * 1) Creates a new list with additional size
     * 2) Copies the old one
     * 3) Inserts old values into a new list
     * @param size - Capacity to add to the list
     */
    private void extend(int size) {
        T[] oldArray = this.arrayList.clone();
        this.arrayList = (T[]) new Object[this.size()+size];
        for (int i = 0; i <= oldArray.length-1; i++) {
            this.arrayList[i] = oldArray[i];
        }
    }


    /**
     * Adds a new value into the list at the end of it.
     * @param toAdd Element to be appended to this list.
     * @return - True if item was added successfully, false otherwise
     * @throws NullPointerException - If toAdd == null
     */
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


    /**
     * Adds every value from a new list to current list.
     * @param toAdd The new sub list to be added.
     * @return
     * @throws NullPointerException
     */
    @Override
    public boolean addAll(ListADT toAdd) throws NullPointerException {
        if (toAdd.isEmpty()) {
            throw new NullPointerException("List to be inserted is null");
        }
        int oldLength = size();
        this.extend(toAdd.size());
        for (int i = oldLength; i < oldLength+toAdd.size(); i++){
            this.arrayList[i] = (T) toAdd.get(i-oldLength);
        }
        return true;
    }


    /**
     * Getter for the list's element
     * @param index Index of element to return.
     * @return - Element of the list
     * @throws IndexOutOfBoundsException
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= this.arrayList.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return arrayList[index];
    }


    /**
     * Private getter for easier functionality
     * @param toFind - Object to find in the list
     * @return - Index of the object
     */
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


    /**
     * Removes the element from the list
     * @param index The index of the element to remove.
     * @return - The value of deleted item
     * @throws IndexOutOfBoundsException
     */
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


    /**
     * Removes the element from the list
     * gets the index of the object and calls remove on index method
     * @param toRemove The element to be removed from this list.
     * @return - Value of removed element
     * @throws NullPointerException
     */
    @Override
    public T remove(T toRemove) throws NullPointerException {
        double check = get(toRemove);
        if ((check % 1) == 0) {
            return this.remove((int) this.get(toRemove));
        }
        return null;
    }


    /**
     * Overwrites the element on the index in the list with a new value
     * @param index    The index of the element to replace.
     * @param toChange Element to be stored at the specified position.
     * @return - Value of changed element
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     */
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


    /**
     * Checks if list is empty
     * @return - True is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return arrayList.length == 0;
    };


    /**
     * Checks if value is presented in the list
     * @param toFind The element whose presence in this list is to be tested.
     * @return - True if present, false otherwise
     * @throws NullPointerException
     */
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


    /**
     * Converts ArrayList into Array
     * @param toHold The array into which the elements of this list are to be
     *               stored, if it is big enough; otherwise, a new array of the same
     *               runtime type is allocated for this purpose.
     * @return - new array
     * @throws NullPointerException
     */
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


    /**
     * Converts ArrayList into Array
     * @return - new array
     */
    @Override
    public T[] toArray() {
        return arrayList;
    }


    /**
     * Constructs iterator object for the list.
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new MyListIterator<>(this.arrayList);
    }
}
