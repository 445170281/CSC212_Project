package Project_212;

import java.util.Scanner;

public class Main {

	static AVLTree<Product> productsTree = new AVLTree<>();
	static AVLTree<Customer> customersTree = new AVLTree<>();
	static AVLTree<Order> ordersTree = new AVLTree<>();

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Loading data...");
		Data.loadProducts(productsTree);
		Data.loadCustomers(customersTree);
		Data.loadOrders(ordersTree, customersTree, productsTree);
		Data.loadReviews(productsTree, customersTree);
		System.out.println("System Ready\n");

		while (true) {

			printMenu();
			int choice = readInt();

			if (choice == 24) {
				System.out.println("Goodbye!");
				return;
			}

			switch (choice) {

			case 1:
				productsTree.inOrderPrint();
				break;
			case 2:
				customersTree.inOrderPrint();
				break;
			case 3:
				ordersTree.inOrderPrint();
				break;
			case 4:
				printProductsWithRatings();
				break;
			case 5:
				editProduct();
				break;
			case 6:
				removeProduct();
				break;
			case 7:
				editCustomer();
				break;
			case 8:
				removeCustomer();
				break;
			case 9:
				editOrder();
				break;
			case 10:
				removeOrder();
				break;
			case 11:
				editReview();
				break;
			case 12:
				addProduct();
				break;
			case 13:
				addCustomer();
				break;
			case 14:
				placeOrder();
				break;
			case 15:
				addReview();
				break;
			case 16:
				viewCustomerHistory();
				break;
			case 17:
				printOutOfStock();
				break;
			case 18:
				printTopRatedProducts();
				break;
			case 19:
				System.out.print("Start date(M/D/Y): ");
				String s = scanner.nextLine();
				System.out.print("End date(M/D/Y): ");
				String e = scanner.nextLine();
				printOrdersBetweenDates(s, e);
				break;
			case 20:
				printCommonHighRatedProducts();
				break;

			// --- NEW FEATURES ---
			case 21:
				printProductsInPriceRange();
				break;

			case 22:
				printCustomersAlphabetically();
				break;

			case 23:
				printCustomersWhoReviewedProduct();
				break;

			default:
				System.out.println("Wrong choice");
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n===== MENU =====");
		System.out.println("1.View Products     2.View Customers    3.View Orders");
		System.out.println("4.Ratings           5.Edit Product      6.Remove Product");
		System.out.println("7.Edit Customer     8.Remove Customer   9.Edit Order");
		System.out.println("10.Remove Order    11.Edit Review      12.Add Product");
		System.out.println("13.Add Customer    14.Place Order      15.Add Review");
		System.out.println("16.History         17.Out of Stock     18.Top 3 Rated");
		System.out.println("19.Orders in range 20.Common Rated     21.Price Range");
		System.out.println("22.Customers A-Z   23.Reviewed By      24.Exit");
		System.out.print("Enter choice: ");
	}
    //==========================================EDIT===========================================================
	private static void editProduct() {

		System.out.print("ID: ");
		Product p = productsTree.search(readInt());

		if (p == null) {
			System.out.println("Not found");
			return;
		}

		System.out.print("New name: ");
		p.setName(scanner.nextLine());

		System.out.print("New price: ");
		p.setPrice(readDouble());

		System.out.print("New stock: ");
		p.setStock(readInt());

		System.out.println("Updated.");
	}

	private static void editCustomer() {
		System.out.print("ID: ");
		Customer c = customersTree.search(readInt());

		if (c == null) {
			System.out.println("Not found");
			return;
		}

		System.out.print("New name: ");
		c.setName(scanner.nextLine());

		System.out.print("New email: ");
		c.setEmail(scanner.nextLine());

		System.out.println("Updated");
	}

	private static void editOrder() {
		System.out.print("ID: ");
		Order o = ordersTree.search(readInt());

		if (o == null) {
			System.out.println("Not found");
			return;
		}

		System.out.print("New status: ");
		o.updateStatus(scanner.nextLine());

		System.out.println("Updated");
	}

	private static void editReview() {

		System.out.print("Product ID: ");
		Product p = productsTree.search(readInt());
		if (p == null)
			return;

		System.out.print("Customer ID: ");
		int cid = readInt();

		Node<Review> cur = p.getReviews().head;

		while (cur != null) {
			if (cur.data.getCustomerId() == cid) {

				System.out.print("New rating: ");
				cur.data.setRating(readInt());

				System.out.print("New comment: ");
				cur.data.setComment(scanner.nextLine());

				System.out.println("Updated");
				return;
			}
			cur = cur.next;
		}

		System.out.println("Review not found.");
	}
    //==================================REMOVE===========================================================================
	private static void removeProduct() {
		System.out.print("ID: ");
		int id = readInt();

		// just making sure the product exists before deleting
		if (productsTree.search(id) == null) {
			System.out.println("Not found");
			return;
		}

		productsTree.delete(id);
		System.out.println("Removed");
	}

	private static void removeCustomer() {
		System.out.print("ID: ");
		int id = readInt();

		if (customersTree.search(id) == null) {
			System.out.println("Not found");
			return;
		}

		customersTree.delete(id);
		System.out.println("Removed");
	}

	private static void removeOrder() {
		System.out.print("ID: ");
		int id = readInt();

		if (ordersTree.search(id) == null) {
			System.out.println("Not found");
			return;
		}

		ordersTree.delete(id);
		System.out.println("Removed");
	}
    //=============================================ADD==================================================================
	private static void addProduct() {

		System.out.print("ID: ");
		int id = readInt();

		if (productsTree.search(id) != null) {
			System.out.println("Exists");
			return;
		}

		System.out.print("Name: ");
		String name = scanner.nextLine();

		System.out.print("Price: ");
		double price = readDouble();

		System.out.print("Stock: ");
		int stock = readInt();

		productsTree.insert(id, new Product(id, name, price, stock));
		System.out.println("Added.");
	}

	private static void addCustomer() {

		System.out.print("ID: ");
		int id = readInt();

		if (customersTree.search(id) != null) {
			System.out.println("Exists");
			return;
		}

		System.out.print("Name: ");
		String name = scanner.nextLine();

		System.out.print("Email: ");
		String email = scanner.nextLine();

		customersTree.insert(id, new Customer(id, name, email));
		System.out.println("Added");
	}

	private static void placeOrder() {

		System.out.print("Order ID: ");
		int oid = readInt();

		if (ordersTree.search(oid) != null) {
			System.out.println("Exists");
			return;
		}

		System.out.print("Customer ID: ");
		Customer c = customersTree.search(readInt());

		if (c == null) {
			System.out.println("Not found");
			return;
		}

		// small cart using linked list
		MyLinkedList<Product> cart = new MyLinkedList<>();
		double total = 0;

		while (true) {
			System.out.print("Product ID (-1 to stop): ");
			int pid = readInt();

			if (pid == -1)
				break;

			Product p = productsTree.search(pid);

			if (p == null || p.isOutOfStock()) {
				System.out.println("We don't have this product");
			} else {
				cart.insert(p);
				total += p.getPrice();
				p.reduceStock(1); // taking one item
			}
		}

		if (total == 0) {
			System.out.println("Empty order.");
			return;
		}

		// This cool thing gave us tody's date
		String date = java.time.LocalDate.now().toString();
		Order o = new Order(oid, c, cart, total, date, "Pending");
		ordersTree.insert(oid, o);
		c.placeOrder(o);

		System.out.println("Order done. Total = " + total);
	}

	private static void addReview() {

		System.out.print("Customer ID: ");
		Customer c = customersTree.search(readInt());

		System.out.print("Product ID: ");
		Product p = productsTree.search(readInt());

		if (c == null || p == null) {
			System.out.println("Not found");
			return;
		}

		System.out.print("Rating: ");
		int r = readInt();

		System.out.print("Comment: ");
		String msg = scanner.nextLine();

		p.addReview(new Review(c, p, r, msg));
		System.out.println("Added");
	}
   //================================VIEW================================================================================
	private static void viewCustomerHistory() {
		System.out.print("Customer ID: ");
		Customer c = customersTree.search(readInt());

		if (c != null)
			c.printOrderHistory();
		else
			System.out.println("Not found");
	}

	private static void printProductsWithRatings() {
		ProductTraversal.printProductsWithRatings(productsTree);
	}

	private static void printOutOfStock() {
		ProductTraversal.printOutOfStock(productsTree);
	}

	private static void printTopRatedProducts() {

		// we collect everything then manually pick top 3
		MyLinkedList<Product> list = new MyLinkedList<>();
		collectProducts(list);

		System.out.println("Top 3:");

		for (int i = 0; i < 3; i++) {
			Product best = getHighest(list);
			if (best != null)
				System.out.println(best.getName() + " | " + best.getAverageRating());
		}
	}

	private static void collectProducts(MyLinkedList<Product> list) {
		ProductTraversal.collectProducts(productsTree, list);
	}

	private static Product getHighest(MyLinkedList<Product> list) {
		double best = -1;
		Product ans = null;

		Node<Product> cur = list.head;
		while (cur != null) {
			if (cur.data.getAverageRating() > best) {
				best = cur.data.getAverageRating();
				ans = cur.data;
			}
			cur = cur.next;
		}

		if (ans != null)
			removeFromList(list, ans);
		return ans;
	}

	private static void removeFromList(MyLinkedList<Product> list, Product p) {
		Node<Product> cur = list.head, prev = null;

		while (cur != null) {
			if (cur.data == p) {
				if (prev == null)
					list.head = cur.next;
				else
					prev.next = cur.next;
				return;
			}
			prev = cur;
			cur = cur.next;
		}
	}

	private static void printOrdersBetweenDates(String s, String e) {
		printOrdersBetweenDatesRec(ordersTree.getRoot(), s, e);
	}

	private static void printOrdersBetweenDatesRec(AVLNode<Order> node, String s, String e) {
		if (node == null)
			return;
		printOrdersBetweenDatesRec(node.left, s, e);
		if (node.data.isWithinDateRange(s, e))
			System.out.println(node.data);
		printOrdersBetweenDatesRec(node.right, s, e);
	}

	private static void printCommonHighRatedProducts() {
		System.out.print("Customer 1: ");
		int id1 = readInt();

		System.out.print("Customer 2: ");
		int id2 = readInt();

		ProductTraversal.printCommonHighRatedProducts(productsTree, id1, id2);
	}

	private static void printProductsInPriceRange() {

		System.out.print("Min price: ");
		double min = readDouble();

		System.out.print("Max price: ");
		double max = readDouble();

		ProductTraversal.printProductsInPriceRange(productsTree, min, max);
	}

	private static void printCustomersAlphabetically() {

		int n = customersTree.getSize();
		if (n <= 0) {
			System.out.println("No customers");
			return;
		}

		// array holds all customers
		Customer[] arr = new Customer[n];

		fillCustomersToArray(customersTree.getRoot(), arr, new int[] { 0 });

		sortCustomersByName(arr);

		for (int i = 0; i < n; i++)
			System.out.println(arr[i]);
	}

	private static void fillCustomersToArray(AVLNode<Customer> node, Customer[] arr, int[] idx) {

		if (node == null)
			return;

		fillCustomersToArray(node.left, arr, idx);
		arr[idx[0]++] = node.data; // store and move
		fillCustomersToArray(node.right, arr, idx);
	}

	// we chose merge sort to make it O(n*log(n))
	private static void sortCustomersByName(Customer[] arr) {
		Merge.mergeSort(arr, 0, arr.length - 1, 0); // 0 = sort by name
	}

	private static void printCustomersWhoReviewedProduct() {

		System.out.print("Product ID: ");
		Product p = productsTree.search(readInt());

		if (p == null) {
			System.out.println("Not found");
			return;
		}

		int n = getReviewersCount(p);

		if (n == 0) {
			System.out.println("No reviews for this product.");
			return;
		}

		Customer[] arr = new Customer[n];

		fillReviewersToArray(p, arr);

		Merge.mergeSort(arr, 0, arr.length - 1, 1); // 1 = sort by ID

		for (int i = 0; i < n; i++)
			System.out.println(arr[i]);
	}

	private static int getReviewersCount(Product p) {
		int count = 0;
		Node<Review> cur = p.getReviews().head;
		while (cur != null) {
			count++;
			cur = cur.next;
		}
		return count;
	}

	private static void fillReviewersToArray(Product p, Customer[] arr) {
		int i = 0;
		Node<Review> cur = p.getReviews().head;
		while (cur != null) {
			arr[i++] = cur.data.getCustomer();
			cur = cur.next;
		}
	}

	private static int readInt() {
		// keeps asking user until he gives a valid int
		while (true) {
			String s = scanner.nextLine().trim();

			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				System.out.print("Invalid number, try again: ");
			}
		}
	}

	private static double readDouble() {
		// same idea as readInt but for doubles
		while (true) {
			String s = scanner.nextLine().trim();

			try {
				return Double.parseDouble(s);
			} catch (Exception e) {
				System.out.print("Invalid amount, try again: ");
			}
		}
	}

}

