package implementations;

import java.util.NoSuchElementException;

/**
 * This is a custom implementation of the Queue Abstract Data Type (ADT) using 
 * a custom doubly linked list implementation (MyDLL). This class provides all 
 * the core functionalities of a queue including enqueue, dequeue, and peek 
 * operations, along with additional helper methods such as size and isEmpty.
 * 
 * @param <T> The type of elements held in this queue.
 */
public class MyQueue<T> implements QueueADT<T> {

    private MyDLL<T> queueList; // Internal data structure to hold queue elements.

    /**
     * Constructs an empty queue using the custom MyDLL implementation.
     */
    public MyQueue() {
        queueList = new MyDLL<>();
    }

    /**
     * Adds an item to the end of this queue.
     * 
     * @param item The item to be added to the queue.
     * @throws IllegalArgumentException if the provided item is null.
     */
    @Override
    public void enqueue(T item) {
        if (item == null) 
            throw new IllegalArgumentException("Item cannot be null.");
        queueList.addLast(item); // Adds the item to the end of the queue.
    }

    /**
     * Removes and returns the item at the front of this queue.
     * 
     * @return The item removed from the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) 
            throw new NoSuchElementException("Queue is empty.");
        return queueList.removeFirst(); // Removes and returns the front item.
    }

    /**
     * Returns the item at the front of this queue without removing it.
     * 
     * @return The item at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    @Override
    public T peek() {
        if (isEmpty()) 
            throw new NoSuchElementException("Queue is empty.");
        return queueList.getFirst(); // Returns the front item without removing it.
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return <code>true</code> if the queue is empty; <code>false</code> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return queueList.size() == 0;
    }

    /**
     * Returns the number of elements in the queue.
     * 
     * @return The current size of the queue as an integer.
     */
    @Override
    public int size() {
        return queueList.size();
    }

    // Note: The implementation assumes the existence of a custom MyDLL class
    // with methods addLast, removeFirst, getFirst, and size.
}
