package javafxapplication3;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;
    private double totalAmount;

    // no arg. Constructor --> to initialize an empty cart
    public Cart() {
        this.products = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // Add product to the cart
    public void addProduct(Product product) {
        this.products.add(product);
        calculateTotal(); // Update the total amount after adding a product
    }

    // Calculate the total amount of all products in the cart
    public double calculateTotal() {
        totalAmount = 0.0; // Reset total amount before recalculating
        for (Product product : products) {
            totalAmount += product.getPrice(); // Sum up the prices of products
        }
        return totalAmount; // Return the calculated total
    }

    // View cart contents
    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Products in your cart:");
            for (Product product : products) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
            System.out.println("Total amount: $" + calculateTotal());
        }
    }

    // Place order and reset cart
    
public void removeProduct(Product product) {
    products.remove(product);
    calculateTotal();
}
    // Clear the cart
    public void clearCart() {
        this.products.clear(); // Remove all products from the cart
        this.totalAmount = 0.0; // Reset the total amount
        System.out.println("Your cart has been cleared.");
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
