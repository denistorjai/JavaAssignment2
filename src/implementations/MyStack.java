package implementations;

import java.util.NoSuchElementException;

public class MyStack<T> implements StackADT<T> {
    private MyArrayList<T> stackList; // Assuming MyArrayList is your custom ArrayList implementation

    public MyStack() {
        stackList = new MyArrayList<>();
    }

    @Override
    public void push(T item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        stackList.add(item); // Assuming add method adds to the top of the stack
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty.");
        return stackList.remove(stackList.size() - 1); // Removing from the top
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty.");
        return stackList.get(stackList.size() - 1); // Returning the top element without removing
    }

    @Override
    public boolean isEmpty() {
        return stackList.size() == 0;
    }

    @Override
    public int size() {
        return stackList.size();
    }
}
