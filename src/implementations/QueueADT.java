package implementations;
import java.util.NoSuchElementException;

public interface QueueADT<T> {

    /**
     * Adds an item to the end of the queue.
     *
     * @param item the item to be added
     * throws IllegalArgumentException if item is null
     */
    void enqueue(T item);

    /**
     * Removes and returns the item at the front of the queue.
     *
     * returns the item at the front of the queue
     * throws NoSuchElementException if the queue is empty
     */
    T dequeue();

    /**
     * Returns the item at the front of the queue without removing it.
     *
     * returns the item at the front of the queue
     * throws NoSuchElementException if the queue is empty
     */
    T peek();

    /**
     * Checks if the queue is empty.
     *
     * returns true if the queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of items in the queue.
     *
     * returns the size of the queue
     */
    int size();
}
