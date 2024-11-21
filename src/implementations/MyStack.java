package implementations;

import java.util.NoSuchElementException;

/**
 * This is a custom implementation of the Stack Abstract Data Type (ADT) using 
 * a custom ArrayList implementation (MyArrayList). This class provides all the 
 * core functionalities of a stack including push, pop, and peek operations, 
 * along with additional helper methods such as size and isEmpty.
 * 
 * @param <T> The type of elements held in this stack.
 */
public class MyStack<T> implements StackADT<T> {

    private MyArrayList<T> stackList; // Internal data structure to hold stack elements.

    /**
     * Constructs an empty stack using the custom MyArrayList implementation.
     */
    public MyStack() {
        stackList = new MyArrayList<>();
    }

    /**
     * Pushes an item onto the top of this stack.
     * 
     * @param item The item to be pushed onto the stack.
     * @throws IllegalArgumentException if the provided item is null.
     */
    @Override
    public void push(T item) {
        if (item == null) 
            throw new IllegalArgumentException("Item cannot be null.");
        stackList.add(item); // Adds the item to the top of the stack.
    }

    /**
     * Removes and returns the object at the top of this stack.
     * 
     * @return The item removed from the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    @Override
    public T pop() {
        if (isEmpty()) 
            throw new NoSuchElementException("Stack is empty.");
        return stackList.remove(stackList.size() - 1); // Removes and returns the top item.
    }

    /**
     * Returns the object at the top of this stack without removing it.
     * 
     * @return The item at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    @Override
    public T peek() {
        if (isEmpty()) 
            throw new NoSuchElementException("Stack is empty.");
        return stackList.get(stackList.size() - 1); // Returns the top item without removing it.
    }

    /**
     * Checks if the stack is empty.
     * 
     * @return <code>true</code> if the stack is empty; <code>false</code> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return stackList.size() == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * 
     * @return The current size of the stack as an integer.
     */
    @Override
    public int size() {
        return stackList.size();
    }

    // Note: The implementation for the other methods of StackADT 
    // (such as toArray, clear, contains, etc.) is not provided here
    // and would need to be added if required.
}
