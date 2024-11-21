package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;

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
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        stackList.add(item);
    }

    /**
     * Removes and returns the object at the top of this stack.
     * 
     * @return The item removed from the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    @Override
    public T pop() {
        if (stackList.isEmpty()) {
            throw new EmptyStackException(); // Change to EmptyStackException
        }
        return stackList.remove(stackList.size() - 1);
    }

    /**
     * Returns the object at the top of this stack without removing it.
     * 
     * @return The item at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    @Override
    public T peek() {
        if (stackList.isEmpty()) {
            throw new EmptyStackException(); // Change to EmptyStackException
        }
        return stackList.get(stackList.size() - 1);
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

    public void clear() {
        stackList.clear(); // Clears the internal MyArrayList
    }

    public boolean contains(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        return stackList.contains(item);
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int currentIndex = stackList.size() - 1; // Start at the top of the stack

            @Override
            public boolean hasNext() {
                return currentIndex >= 0; // There are still elements to iterate over
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the stack.");
                }
                return (Integer) stackList.get(currentIndex--); // Get the element and move to the previous index
            }
        };
    }

    public int search(Integer one) {
        for (int i = stackList.size() - 1; i >= 0; i--) {
            if (stackList.get(i).equals(one)) {
                return stackList.size() - i; // Position from the top of the stack (1-based index)
            }
        }
        return -1; // Return -1 if the item is not found
    }

    @SuppressWarnings("unchecked")
	public Object[] toArray(Object[] array) {
        // Ensure the provided array is big enough to hold the stack's elements
        if (array.length < stackList.size()) {
            array = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), stackList.size());
        }

        // Fill the array with the elements of the stack, in reverse order
        for (int i = 0; i < stackList.size(); i++) {
            array[i] = stackList.get(stackList.size() - 1 - i); // Reverse order
        }

        return array;
    }

    public boolean stackOverflow() {
        final int MAX_CAPACITY = 1000; 
        
        // Check if the size exceeds or equals the maximum capacity
        return stackList.size() >= MAX_CAPACITY;
    }


    public boolean equals(Object obj) {
        // Check if both stacks are the same instance
        if (this == obj) {
            return true;
        }
        
        // Check if the object is null or not of the same class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        // Cast the object to MyStack type
        MyStack<?> other = (MyStack<?>) obj;
        
        // Check if the sizes of both stacks are equal
        if (this.size() != other.size()) {
            return false;
        }
        
        // Compare the elements in the same order (from bottom to top of the stack)
        for (int i = 0; i < this.size(); i++) {
            if (!this.stackList.get(i).equals(other.stackList.get(i))) {
                return false;
            }
        }
        
        return true;
    }


    // Note: The implementation for the other methods of StackADT 
    // (such as toArray, clear, contains, etc.) is not provided here
    // and would need to be added if required.
}
