package implementations;

import java.util.NoSuchElementException;

public class MyQueue<T> implements QueueADT<T> {
    private MyDLL<T> queueList;

    public MyQueue() {
        queueList = new MyDLL<>();
    }

    @Override
    public void enqueue(T item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        queueList.addLast(item); // Adding to the end of the queue
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        return queueList.removeFirst(); // Removing from the front of the queue
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        return queueList.getFirst(); // Returning the front item without removing
    }

    @Override
    public boolean isEmpty() {
        return queueList.size() == 0;
    }

    @Override
    public int size() {
        return queueList.size();
    }
}
