package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class MyDLL<T> implements ListADT<T>, Iterator<T> {

    private int size = 0;

    private Node<T> head, tail = null;

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
        return size;
    }

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

    private int getNode(T data) {
        Node<T> current = head;
        int i;
        for (i = 0; i < size; i++) {
            if (current.getData().equals(data)) {return i;}
            current = current.getNextNode();
        }
        return -1;
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i <= index; i++) {
            if (current == null) {return null;}
            if (i == index) {return current;}
            current = current.getNextNode();
        }
        return current;
    }

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

    @Override
    public T remove(T toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove a null value");
        }if (!contains(toRemove)){return null;}
        return remove(getNode(toRemove));
    }

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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

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

    @Override
    public Iterator iterator() {
        return new MyListIterator(this.toArray());
    }
}

