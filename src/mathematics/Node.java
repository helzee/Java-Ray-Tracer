package mathematics;

public class Node<T> {

    private T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return this.value;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public boolean hasNext() {
        return (this.next != null);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getLast() {
        Node<T> temp = this;

        while (temp.hasNext()) {
            temp = temp.getNext();
        }

        return temp;
    }
    //track length as nodes are appended/popped? check how often this function is called in program and how big list gets.
    public int length() {
        Node<T> temp = this;
        int counter = 1;

        while (temp.hasNext()) {
            temp = temp.getNext();
            counter++;
        }

        return counter;
    }

    public void append(T value) {
        this.getLast().setNext(new Node<T>(value));
    }

    public void append(int index, T value) {
        Node<T> temp = this;

        for (int i = 0; i < index; i++)
            temp = temp.getNext();

        Node<T> next = temp.getNext();
        temp.setNext(new Node<T>(value, next));
    }

    public T pop() {
        Node<T> temp = this;

        while (temp.getNext().hasNext())
            temp = temp.getNext();

        T val = temp.getNext().getValue();
        temp.setNext(null);

        return val;
    }

    public T get(int index) {
        Node<T> temp = this;

        for (int i = 0; i < index; i++)
            temp = temp.getNext();

        return temp.getValue();
    }

    public void set(int index, T value) {
        Node<T> temp = this;

        for (int i = 0; i < index; i++)
            temp = temp.getNext();

        temp.setValue(value);
    }

    public String toString() {
        return value + "-->" + next;
    }
}
