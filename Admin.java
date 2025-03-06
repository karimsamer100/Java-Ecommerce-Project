package javafxapplication3;

import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private String role;
    private int workingHours;

    public Admin(String username, String password, String dateOfBirth, String role, int workingHours) {
        super(username, password, dateOfBirth);  // Call parent (User) constructor
        this.role = role;
        this.workingHours = workingHours;
    }
 @Override
    public void displayInfo() {
        System.out.println("Admin Info: " + getUsername() + " - Role: " + role);
    }
    // Getters and Setters 
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getWorkingHours() { return workingHours; }
    public void setWorkingHours(int workingHours) { this.workingHours = workingHours; }

    // Admin-specific actions (e.g., viewing users/products/orders)
    public void showUsers(List<Customer> customers) {
        System.out.println("Users:");
        for (Customer customer : customers) {            // kan momken n3mlha bel i bs kda a7sn
            System.out.println(customer.getUsername());  // Access via inherited getter
        }
    }

    public void showProducts(List<Product> products) {
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println(product.getName());
        }
    }

    public void showOrders(List<Order> orders) {
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.println(order.getOrderDetails());
        }
    }

    // Create a new product
    public static void createProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();

        Category category = null;
        for (Category cat : Database.categories) {
            if (cat.getName().equalsIgnoreCase(categoryName)) { //equalsIgnoreCase: to ignore case differences
                category = cat;
                break;
            }
        }
        if (category == null) {
            System.out.println("Category not found. Please create the category first.");
            return;
        }

        Product newProduct = new Product(name, price, description, stock, category);
        Database.products.add(newProduct);
        System.out.println("Product added successfully!");
    }

    // Update an existing product
    public static void updateProduct(Scanner scanner) {
        System.out.print("Enter the name of the product to update: ");
        String productName = scanner.nextLine();

        Product productToUpdate = null;
        for (Product product : Database.products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToUpdate = product;
                break;
            }
        }
        if (productToUpdate == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new price (or -1 to skip): ");
        double newPrice = scanner.nextDouble();
        if (newPrice >= 0) productToUpdate.setPrice(newPrice);

        System.out.print("Enter new stock (or -1 to skip): ");
        int newStock = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (newStock >= 0) productToUpdate.setStock(newStock);

        System.out.println("Product updated successfully!");
    }

    // Delete a product
    public void deleteProduct(Scanner scanner) {
        System.out.print("Enter the name of the product to delete: ");
        String productName = scanner.nextLine();

        Product productToDelete = null;
        for (Product product : Database.products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToDelete = product;
                break;
            }
        }
        if (productToDelete != null) {
            Database.products.remove(productToDelete);
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }
    
    public static void createCategory(Scanner scanner) {
    System.out.print("Enter category name: ");
    String categoryName = scanner.nextLine();

    // Check if the category already exists
    for (Category cat : Database.categories) {
        if (cat.getName().equalsIgnoreCase(categoryName)) {
            System.out.println("Category already exists.");
            return;
        }
    }

    // Ask for category description
    System.out.print("Enter category description: ");
    String categoryDescription = scanner.nextLine();  // Declare and initialize the description variable

    // Create a new category and add it to the database
    Category newCategory = new Category(categoryName, categoryDescription);
    Database.categories.add(newCategory);
    System.out.println("Category created successfully!");
}



}
