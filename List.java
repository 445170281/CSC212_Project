package csc212.copy;

public interface List<T> {
    boolean empty();
    boolean full();
    void findFirst();
    void findNext();
    boolean last();
    T retrieve();
    void update(T data);
    void insert(T data);
    void remove();
    void printAll();
}

