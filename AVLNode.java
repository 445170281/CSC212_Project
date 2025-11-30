package Project_212;

public class AVLNode<T> {
    public int key;
    public T data;
    public int height;
    public int size;
    public AVLNode<T> left;
    public AVLNode<T> right;

    public AVLNode(int key, T data) {
        this.key = key;
        this.data = data;
        this.height = 0;
        this.size = 1;
        this.left = null;
        this.right = null;
    }
}
