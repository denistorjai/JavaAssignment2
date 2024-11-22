package implementations;

public class Node<T> {

    private Node<T> prev;
    private Node<T> next;
    private T data;


    /**
     * Constructor of Node class that accepts only one parameter
     * @param data - Anything (any type)
     */
    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    /**
     * Getter for next node
     * @return - next node
     */
    public Node<T> getNextNode() {
        return next;
    }

    /**
     * Getter for previous node
     * @return - previous node
     */
    public Node<T> getPrevNode() {
        return prev;
    }

    /**
     * Getter for data inside the node
     * @return - data inside the node
     */
    public T getData() {
        return data;
    }


    /**
     * Setter for data inside the node
     * @param data - new data
     */
    public void setData(T data) {
        this.data = data;
    }


    /**
     * Setter for next node
     * @param node - new next node
     */
    public void setNextNode(Node<T> node) {
        this.next = node;
    }


    /**
     * Setter for previous node
     * @param node - new previous node
     */
    public void setPrevNode(Node<T> node) {
        this.prev = node;
    }

}
