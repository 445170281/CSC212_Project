package csc212.copy;

import java.io.BufferedReader;
import java.io.FileReader;

public class Data {

    public static void loadProducts(AVLTree<Product> products) {
        loadFile("products.csv", new LineProcessor() {
            public void process(String[] parts) {
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int stock = Integer.parseInt(parts[3].trim());
                    products.insert(id, new Product(id, name, price, stock));
                } catch (Exception e) { System.out.println("Error parsing product"); }
            }
        });
        System.out.println("Products Loaded.");
    }

    public static void loadCustomers(AVLTree<Customer> customers) {
        loadFile("customers.csv", new LineProcessor() {
            public void process(String[] parts) {
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    customers.insert(id, new Customer(id, name, email));
                } catch (Exception e) { System.out.println("Error parsing customer"); }
            }
        });
        System.out.println("Customers Loaded.");
    }

    public static void loadOrders(AVLTree<Order> orders, AVLTree<Customer> customers, AVLTree<Product> products) {
        loadFile("orders.csv", new LineProcessor() {
            public void process(String[] parts) {
                try {
                    int orderId = Integer.parseInt(parts[0].trim());
                    int customerId = Integer.parseInt(parts[1].trim());
                    String[] productIds = parts[2].split(";");
                    double totalPrice = Double.parseDouble(parts[3].trim());
                    String date = parts[4].trim();
                    String status = parts[5].trim();

                    Customer customer = customers.search(customerId);
                    if (customer == null) return;

                    MyLinkedList<Product> orderProducts = new MyLinkedList<>();
                    for (String pid : productIds) {
                        Product p = products.search(Integer.parseInt(pid.trim()));
                        if (p != null) orderProducts.insert(p);
                    }

                    Order order = new Order(orderId, customer, orderProducts, totalPrice, date, status);
                    customer.getOrders().insert(order); 
                    orders.insert(orderId, order);      
                } catch (Exception e) { System.out.println("Error parsing order"); }
            }
        });
        System.out.println("Orders Loaded.");
    }

    public static void loadReviews(AVLTree<Product> products, AVLTree<Customer> customers) {
        loadFile("reviews.csv", new LineProcessor() {
            public void process(String[] parts) {
                try {
                    int productId = Integer.parseInt(parts[1].trim());
                    int customerId = Integer.parseInt(parts[2].trim());
                    int rating = Integer.parseInt(parts[3].trim());
                    String comment = parts[4].trim();

                    Product p = products.search(productId);
                    Customer c = customers.search(customerId);

                    if (p != null && c != null) {
                        p.addReview(new Review(c, p, rating, comment));
                    }
                } catch (Exception e) { System.out.println("Error parsing review"); }
            }
        });
        System.out.println("Reviews Loaded.");
    }

    private static void loadFile(String fileName, LineProcessor processor) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (!line.trim().isEmpty()) processor.process(line.split(","));
            }
        } catch (Exception e) { System.out.println("File not found: " + fileName); }
    }

    interface LineProcessor { void process(String[] parts); }
}
