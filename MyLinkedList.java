package csc212.copy;

public class MyLinkedList<T> implements List<T> {
    Node<T> head;
    private Node<T> current;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.current = null;
        this.size = 0;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean full() {
        return false; // LinkedList is never full unless memory is full
    }

    @Override
    public void findFirst() {
        current = head;
    }

    @Override
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    @Override
    public boolean last() {
        return current != null && current.next == null;
    }

    @Override
    public T retrieve() {
        if (current != null) {
            return current.data;
        }
        return null;
    }

    @Override
    public void update(T data) {
        if (current != null) {
            current.data = data;
        }
    }

    @Override
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = current = newNode;
        } else if (current == null) {
            Node<T> temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
            current = newNode;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode;
        }
        size++;
    }

    @Override
    public void remove() {
        if (current == null || head == null)
            return;

        if (current == head) {
            head = head.next;
            current = head;
        } else {
            Node<T> temp = head;
            while (temp.next != current) {
                temp = temp.next;
            }
            temp.next = current.next;
            current = temp.next;
        }
        size--;
    }

    @Override
    public void printAll() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}