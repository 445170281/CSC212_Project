package csc212.copy;

import java.io.BufferedReader;
import java.io.FileReader;

public class Data {

    public static void loadProducts(MyLinkedList<Product> products) {
        loadFile("products.csv", (parts) -> {
            try {
                // ترتيب الأعمدة في CSV بناء على بياناتك
                int id = Integer.parseInt(parts[0].trim());    // بالرغم من الاسم الغير معتاد "productld" نستخدم الترتيب فقط
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int stock = Integer.parseInt(parts[3].trim());
                products.insert(new Product(id, name, price, stock));
            } catch (Exception e) {
                System.out.println("تجاوز خطأ في سطر منتج: " + String.join(",", parts));
            }
        });
        System.out.println("تم تحميل المنتجات: " + products.size());
    }

    public static void loadCustomers(MyLinkedList<Customer> customers) {
        loadFile("customers.csv", (parts) -> {
            try {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String email = parts[2].trim();
                customers.insert(new Customer(id, name, email));
            } catch (Exception e) {
                System.out.println("تجاوز خطأ في سطر عميل: " + String.join(",", parts));
            }
        });
        System.out.println("تم تحميل العملاء: " + customers.size());
    }

    public static void loadOrders(MyLinkedList<Order> orders, MyLinkedList<Customer> customers, MyLinkedList<Product> products) {
        loadFile("orders.csv", (parts) -> {
            try {
                int orderId = Integer.parseInt(parts[0].trim());
                int customerId = Integer.parseInt(parts[1].trim());
                // لاحظ هنا استخدام الفاصلة المنقوطة كفاصل في معرفات المنتجات
                String[] productIds = parts[2].split(";");
                double totalPrice = Double.parseDouble(parts[3].trim());
                String date = parts[4].trim();
                String status = parts[5].trim();

                Customer customer = findCustomerById(customers, customerId);
                if (customer == null) return;

                MyLinkedList<Product> orderProducts = new MyLinkedList<>();
                for (String pid : productIds) {
                    Product p = findProductById(products, Integer.parseInt(pid.trim()));
                    if (p != null) orderProducts.insert(p);
                }

                Order order = new Order(orderId, customer, orderProducts, totalPrice, date, status);
                customer.getOrders().insert(order);
                orders.insert(order);
            } catch (Exception e) {
                System.out.println("تجاوز خطأ في سطر طلب: " + String.join(",", parts));
            }
        });
        System.out.println("تم تحميل الطلبات: " + orders.size());
    }

    public static void loadReviews(MyLinkedList<Product> products, MyLinkedList<Customer> customers) {
        loadFile("reviews.csv", (parts) -> {
            try {
                // ترتيب الأعمدة في ملف التقييم
                int reviewId = Integer.parseInt(parts[0].trim());  // غير مستخدم لكن نبدأ بمراعاته
                int productId = Integer.parseInt(parts[1].trim());
                int customerId = Integer.parseInt(parts[2].trim());
                int rating = Integer.parseInt(parts[3].trim());
                String comment = parts[4].trim();

                Product product = findProductById(products, productId);
                Customer customer = findCustomerById(customers, customerId);

                if (product != null && customer != null) {
                    Review review = new Review(customer, product, rating, comment);
                    product.addReview(review);
                }
            } catch (Exception e) {
                System.out.println("تجاوز خطأ في سطر تقييم: " + String.join(",", parts));
            }
        });
        System.out.println("تم تحميل التقييمات.");
    }

    private static void loadFile(String fileName, LineProcessor processor) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean header = true;   // لتخطي الرأس

            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;

                // تفصيل السطر حسب الفاصلة ',' لأن ملفاتك csv بصيغة نص مع فاصلة
                processor.process(line.split(","));
            }
        } catch (Exception e) {
            System.out.println("تعذّر تحميل الملف: " + fileName);
            e.printStackTrace();
        }
    }

    private static Product findProductById(MyLinkedList<Product> products, int id) {
        Node<Product> current = products.head;
        while (current != null) {
            if (current.data.getProductId() == id) return current.data;
            current = current.next;
        }
        return null;
    }

    private static Customer findCustomerById(MyLinkedList<Customer> customers, int id) {
        Node<Customer> current = customers.head;
        while (current != null) {
            if (current.data.getCustomerId() == id) return current.data;
            current = current.next;
        }
        return null;
    }

    interface LineProcessor {
        void process(String[] parts);
    }
}