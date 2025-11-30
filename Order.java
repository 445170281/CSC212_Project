package Project_212;

public class Order {
    private int orderId;
    private Customer customer;
    private MyLinkedList<Product> products;
    private double totalPrice;
    private String date;
    private String status; // مثل: pending, shipped, delivered, canceled

    public Order(int orderId, Customer customer, MyLinkedList<Product> products,
                 double totalPrice, String date, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MyLinkedList<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

       public boolean isWithinDateRange(String start, String end) {
        String d  = normalize(date);
        String s2 = normalize(start);
        String e2 = normalize(end);

        return d.compareTo(s2) >= 0 && d.compareTo(e2) <= 0;
    }

    private String normalize(String d) {
        // d = "M/D/YYYY" أو "MM/DD/YYYY"
        String[] parts = d.split("/");

        String month = parts[0];
        String day   = parts[1];
        String year  = parts[2];

        if (month.length() == 1) month = "0" + month;
        if (day.length() == 1)   day   = "0" + day;

        return year + "-" + month + "-" + day; 
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer ID: " + customer.getCustomerId() +
                ", Total Price: $" + totalPrice +
                ", Date: " + date +
                ", Status: " + status;
    }
}

