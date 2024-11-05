package implementations;

public class Node<T> {

    private Node<T> prev;
    private Node<T> next;
    private T data;

    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public Node<T> getNextNode() {
        return next;
    }

    public Node<T> getPrevNode() {
        return prev;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(Node<T> node) {
        this.next = node;
    }

    public void setPrevNode(Node<T> node) {
        this.prev = node;
    }

}
