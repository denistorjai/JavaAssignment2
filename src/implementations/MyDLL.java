package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class MyDLL<T> implements ListADT<T> {

    private int size = 0;

    private Node<T> head, tail = null;


    /**
     * Getter for list's size
     * @return - current size of the list
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Clears the list
     */
    @Override
    public void clear() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.getNextNode();
            current.setPrevNode(null);
            current.setNextNode(null);
            current.setData(null);
            current = next;
            size--;
        }
        head = tail = null;
    }


    /**
     * Adds a new item into the list at specified index
     * @param index The index at which the specified element is to be inserted. The
     *              element is inserted before the existing element at [index], or
     *              at the end if index is equal to the size (<code>size()</code>).
     * @param toAdd The element to be inserted.
     * @return - True if element was inserted, false otherwise
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     */
    @Override
    public boolean add(int index, T toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (toAdd == null) {
            throw new NullPointerException("Object to be added is null");
        }
        Node<T> newNode = new Node<>(toAdd);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.setNextNode(head);
            head.setPrevNode(newNode);
            head = newNode;
        } else if (index == size) {
            newNode.setPrevNode(tail);
            tail.setNextNode(newNode);
            tail = newNode;
        } else {
            Node<T> current = getNode(index);
            Node<T> prev = current.getPrevNode();
            newNode.setNextNode(current);
            newNode.setPrevNode(prev);
            if (prev != null) {
                prev.setNextNode(newNode);
            }
            current.setPrevNode(newNode);
        }
        size++;
        return true;
    }


    /**
     * Adds a new element at the end of the list
     * @param toAdd Element to be appended to this list.
     * @return - True if item was inserted, false otherwise
     * @throws NullPointerException
     */
    @Override
    public boolean add(T toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add a null value");
        }
        Node<T> newNode = new Node<>(toAdd);
        if (head == null) {
            head = tail = newNode;
            head.setPrevNode(null);
            tail.setNextNode(null);
        }else {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
            tail.setNextNode(null);
        }
        size++;
        return true;
    }


    /**
     * Adds all elements from inputted list into the current one
     * @param toAdd The new sub list to be added.
     * @return - True if operation is successfully, false otherwise
     * @throws NullPointerException
     */
    @Override
    public boolean addAll(ListADT toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add a null value");
        }
        for(int i = 0; i < toAdd.size(); i++) {
            add((T) toAdd.get(i));
        }
        return true;
    }


    /**
     * Getter for the Node
     * @param data
     * @return - Index of the node
     */
    private int getNode(T data) {
        Node<T> current = head;
        int i;
        for (i = 0; i < size; i++) {
            if (current.getData().equals(data)) {return i;}
            current = current.getNextNode();
        }
        return -1;
    }


    /**
     * Getter for Node
     * @param index
     * @return - Node at the index
     */
    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i <= index; i++) {
            if (current == null) {return null;}
            if (i == index) {return current;}
            current = current.getNextNode();
        }
        return current;
    }


    /**
     * Getter for the data in the list
     * @param index Index of element to return.
     * @return - Data of the node under specified index
     * @throws IndexOutOfBoundsException
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0 && size == 0){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).getData();
    }


    /**
     * Removes node at the index and
     * connects nodes upons the conditions
     * @param index The index of the element to remove.
     * @return - Data of removed node
     * @throws IndexOutOfBoundsException
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0 && size == 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNode(index);
        Node<T> prev = current.getPrevNode();
        Node<T> next = current.getNextNode();

        if (prev != null && next != null) {
            prev.setNextNode(next);
            next.setPrevNode(prev);
        } else if (prev != null && next == null) {
            prev.setNextNode(null);
        } else if (prev == null && next != null) {
            head = next;
        }
        T data = current.getData();
        current.setData(null);
        current.setNextNode(null);
        current.setPrevNode(null);
        size--;
        return data;
    }


    /**
     * Removes the object in the list
     * @param toRemove The element to be removed from this list.
     * @return - Data of removed node
     * @throws NullPointerException
     */
    @Override
    public T remove(T toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove a null value");
        }if (!contains(toRemove)){return null;}
        return remove(getNode(toRemove));
    }


    /**
     * Setter for the node's data at specified index
     * @param index    The index of the element to replace.
     * @param toChange Element to be stored at the specified position.
     * @return - Node's old data
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     */
    @Override
    public T set(int index, T toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (toChange == null) {
            throw new NullPointerException("Cannot change a null value");
        }
        if (index == 0 && size == 0){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNode(index);
        T oldValue = current.getData();
        current.setData(toChange);
        return oldValue;
    }


    /**
     * Checks if list is empty
     * @return - True if it's empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Checks if the specified element is in the list
     * @param toFind The element whose presence in this list is to be tested.
     * @return - True if item is present, false otherwise
     * @throws NullPointerException
     */
    @Override
    public boolean contains(T toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Object to be found is null");
        }
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (toFind.equals(current.getData())) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }


    /**
     * Converts DLL into array.
     * If inputted array's size is lower than the list's size,
     * creates a new array instead of an old one.
     * @param toHold The array into which the elements of this list are to be
     *               stored, if it is big enough; otherwise, a new array of the same
     *               runtime type is allocated for this purpose.
     * @return - new array
     * @throws NullPointerException
     */
    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Object is null");
        }
        if (toHold.length < size) {
            toHold = (T[]) Array.newInstance(toHold.getClass().getComponentType(), size);
        }
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.getData();
            current = current.getNextNode();
        }
        return toHold;
    }


    /**
     * Converts DLL into Array
     * @return - new array
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNextNode();
        }
        return array;
    }


    /**
     * Creates an iterator object for current DLL
     * @return - new iterator instance
     */
    @Override
    public Iterator iterator() {
        return new MyListIterator(this.toArray());
    }

//    public void addLast(T item) {
//        if (item == null) {
//            throw new NullPointerException("Cannot add a null value");
//        }
//
//        Node<T> newNode = new Node<>(item);
//
//        if (head == null) { // List is empty
//            head = tail = newNode;
//            head.setPrevNode(null);
//            tail.setNextNode(null);
//        } else { // List is not empty
//            tail.setNextNode(newNode);
//            newNode.setPrevNode(tail);
//            tail = newNode;
//            tail.setNextNode(null);
//        }
//
//        size++;
//    }


//    public T removeFirst() {
//        if (head == null) { // List is empty
//            throw new NoSuchElementException("The list is empty.");
//        }
//
//        T data = head.getData(); // Save the data of the head node
//        if (head == tail) { // If the list contains only one element
//            head = tail = null;
//        } else { // More than one element in the list
//            Node<T> next = head.getNextNode();
//            head.setData(null); // Clear the data of the current head
//            head.setNextNode(null); // Disconnect the current head
//            if (next != null) {
//                next.setPrevNode(null); // Set the new head's prev to null
//            }
//            head = next; // Update head reference
//        }
//
//        size--; // Decrease size
//        return data; // Return the removed data
//    }
//
//    public T getFirst() {
//        if (head == null) { // List is empty
//            throw new NoSuchElementException("The list is empty.");
//        }
//        return head.getData(); // Return the data of the head node
//}
}

