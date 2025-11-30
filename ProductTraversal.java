package Project_212;

public class ProductTraversal {

    public static void inOrderProducts(AVLNode<Product> node, int mode,
                                       double min, double max,
                                       int id1, int id2,
                                       MyLinkedList<Product> collector) {

        if (node == null) return;

        inOrderProducts(node.left, mode, min, max, id1, id2, collector);

        Product p = node.data;

        switch (mode) {
            case 1: // ratings
                System.out.println(p.getName() + " | " + p.getAverageRating());
                break;

            case 2: // out of stock
                if (p.isOutOfStock()) System.out.println(p);
                break;

            case 3: // collect all
                collector.insert(p);
                break;

            case 4: // price range
                if (p.getPrice() >= min && p.getPrice() <= max)
                    System.out.println(p);
                break;

            case 5: // common-rated by 2 customers
                boolean f1 = false, f2 = false;
                Node<Review> r = p.getReviews().head;

                while (r != null) {
                    if (r.data.getCustomerId() == id1) f1 = true;
                    if (r.data.getCustomerId() == id2) f2 = true;
                    r = r.next;
                }

                if (f1 && f2 && p.getAverageRating() > 4)
                    System.out.println(p.getName());
                break;
        }

        inOrderProducts(node.right, mode, min, max, id1, id2, collector);
    }

    public static void collectProducts(AVLTree<Product> tree, MyLinkedList<Product> list) {
        inOrderProducts(tree.getRoot(), 3, 0, 0, 0, 0, list);
    }

    public static void printProductsWithRatings(AVLTree<Product> tree) {
        inOrderProducts(tree.getRoot(), 1, 0, 0, 0, 0, null);
    }

    public static void printOutOfStock(AVLTree<Product> tree) {
        inOrderProducts(tree.getRoot(), 2, 0, 0, 0, 0, null);
    }

    public static void printProductsInPriceRange(AVLTree<Product> tree, double min, double max) {
        inOrderProducts(tree.getRoot(), 4, min, max, 0, 0, null);
    }

    public static void printCommonHighRatedProducts(AVLTree<Product> tree, int id1, int id2) {
        inOrderProducts(tree.getRoot(), 5, 0, 0, id1, id2, null);
    }
}

