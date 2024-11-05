package implementations;

import utilities.Iterator;
import utilities.ListADT;

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
//        int i = 0;
//        if (head == null) {
//            return 0;
//        }else {
//            Node current = head;
//            while (current != null) {
//                i++;
//                current = (Node) current.getNextNode();
//            }
//        }
//        return i;

        return size;
    }

    @Override
    public void clear() {
        Node current = head;
        while (current != null) {
            Node next = (Node) current.getNextNode();
            current.setPrevNode(null);
            current.setNextNode(null);
            current.setData(null);
            current = next;
        }
        head = tail = null;
    }

    @Override
    public boolean add(int index, Object toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (toAdd == null) {
            throw new NullPointerException("Object to be added is null");
        }
        Node current = (Node) this.get(index);
        Node newNode = new Node(toAdd);
        if (current.getNextNode() == null) {
            //Extend the list
            current.setNextNode(newNode);
            newNode.setPrevNode(current);
            newNode.setNextNode(null);
        }else {
            //Insert into the list
            newNode.setNextNode(current);
            Node prev = (Node) current.getPrevNode();
            current.setPrevNode(newNode);
            prev.setNextNode(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean add(Object toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add a null value");
        }
        Node newNode = new Node(toAdd);
        if (head == null) {
            head = tail = newNode;
            head.setPrevNode(null);
            tail.setNextNode(newNode);
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

        return true;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        return (T) current.getData();
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = (Node) this.get(index);
        Node prev = (Node) current.getPrevNode();
        Node next = (Node) current.getNextNode();
        prev.setNextNode(next);
        next.setPrevNode(prev);
        current.setData(null);
        current.setNextNode(null);
        current.setPrevNode(null);
        size--;
        return current;
    }

    @Override
    public Object remove(Object toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove a null value");
        }
        if (this.contains(toRemove)) {
            Node current = head;
            for (int i = 0; i < size-1; i++) {
                if (toRemove.equals(current)) {
                    break;
                }
                current = (Node) current.getNextNode();
            }
            Node prev = (Node) current.getPrevNode();
            Node next = (Node) current.getNextNode();
            prev.setNextNode(next);
            next.setPrevNode(prev);
            current.setData(null);
            current.setNextNode(null);
            current.setPrevNode(null);
            size--;
            return current;
        }
        return null;
    }

    @Override
    public Object set(int index, Object toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (toChange == null) {
            throw new NullPointerException("Cannot change a null value");
        }
        Node current = (Node) this.get(index);
        Node newNode = new Node(toChange);
        newNode.setPrevNode(current.getPrevNode());
        newNode.setNextNode(current.getNextNode());
        current.setPrevNode(null);
        current.setNextNode(null);
        return current;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Object to be found is null");
        }
        Node current = head;
        for (int i = 0; i < size-1; i++) {
            if (toFind.equals(current)) {
                return true;
            }
            current = (Node) current.getNextNode();
        }
        return false;
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Object is null");
        }
        if (toHold.length < size) {
            toHold = (T[]) new Object[size];
        }
        Node<T> current = head;
        for (int i = 0; i < size-1; i++) {
            toHold[i] = current.getData();
            current = current.getNextNode();
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        T[] array = (T[]) new Object[size];
        Node<T> current = head;
        for (int i = 0; i < size-1; i++) {
            array[i] = current.getData();
            current = (Node) current.getNextNode();
        }
        return array;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}

