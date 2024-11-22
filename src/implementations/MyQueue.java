package implementations;
import exceptions.EmptyQueueException;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class MyQueue<T> implements Iterable<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    

    // Node class for the queue (internal class)
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Constructor for MyQueue
    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Add an element to the queue
    public void enqueue(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }

        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    

    // Remove an element from the queue
    public T dequeue() {
        if (front == null) {
            throw new EmptyQueueException("Queue is empty");  // Throws exception if the queue is empty
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;  // If the queue becomes empty
        }
        size--;
        return item;
    }


    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the size of the queue
    public int size() {
        return size;
    }

    // Implementing Iterable<T> interface to allow iteration
    @Override
    public Iterator<T> iterator() {
        return new MyQueueIterator();
    }

    // Inner iterator class for MyQueue
    private class MyQueueIterator implements Iterator<T> {
        private Node<T> current;

        public MyQueueIterator() {
            current = front;
        }

        // Check if there is another element in the queue
        @Override
        public boolean hasNext() {
            return current != null;
        }

        // Return the next element in the queue
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the queue");
            }
            T item = current.data;
            current = current.next;
            return item;
        }

        // Optional: Remove operation (not used in this example)
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }


    public void dequeueAll() {
        front = null;
        rear = null;
        size = 0;
    }

    public T peek() {
        if (front == null) {
            throw new EmptyQueueException("Queue is empty.");
        }
        return front.data;
    }

    @SuppressWarnings("unchecked")
	public T[] toArray(T[] returnArray) {
        // If the passed array is smaller than the size, create a new one
        if (returnArray.length < size) {
            returnArray = (T[]) java.lang.reflect.Array.newInstance(returnArray.getClass().getComponentType(), size);
        }

        Node<T> current = front;
        int index = 0;

        // Copy elements from the queue into the array
        while (current != null) {
            returnArray[index++] = current.data;
            current = current.next;
        }

        // If the array is larger than the size, set the remaining elements to null
        if (returnArray.length > size) {
            returnArray[size] = null;
        }

        return returnArray;
    }


	public boolean isFull() {
		return false;
	}

	public boolean contains(T item) {
	    if (item == null) {
	        throw new NullPointerException("Item cannot be null");
	    }
	    Node<T> current = front;
	    while (current != null) {
	        if (current.data.equals(item)) {
	            return true;
	        }
	        current = current.next;
	    }
	    return false;
	}

	public int search(T item) {
	    int index = 1;  // Start index at 1 as per your test expectations
	    Node<T> current = front;
	    
	    while (current != null) {
	        if (current.data.equals(item)) {
	            return index;  // Return the index when item is found
	        }
	        current = current.next;
	        index++;
	    }
	    
	    return -1;  // Return -1 if the item is not found
	}
	
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true; // Same object
	    }
	    
	    // Check if the object is of the correct type
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    MyQueue<?> other = (MyQueue<?>) obj; // Cast the object to MyQueue
	    
	    // If the sizes don't match, the queues are not equal
	    if (this.size != other.size) {
	        return false;
	    }
	    
	    // Compare elements in both queues
	    Node<T> currentThis = this.front;
	    Node<?> currentOther = other.front;
	    
	    while (currentThis != null) {
	        if (!currentThis.data.equals(currentOther.data)) {
	            return false; // If any element doesn't match, return false
	        }
	        currentThis = currentThis.next;
	        currentOther = currentOther.next;
	    }
	    
	    return true; // All elements matched, so the queues are equal
	}
	
}


