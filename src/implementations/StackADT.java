package implementations;

public interface StackADT<T> {
	/**
     * Adds an item to the top of the stack.
     *
     * @param item the item to be added
     * throws IllegalArgumentException if item is null
     */
    void push(T item);

    /**
     * Removes and returns the item at the top of the stack.
     *
     * returns the item at the top of the stack
     * throws NoSuchElementException if the stack is empty
     */
    T pop();

    /**
     * Returns the item at the top of the stack without removing it.
     *
     * returns the item at the top of the stack
     * throws NoSuchElementException if the stack is empty
     */
    T peek();

    /**
     * Checks if the stack is empty.
     *
     * returns true if the stack is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of items in the stack.
     *
     * returns the size of the stack
     */
    int size();
}
