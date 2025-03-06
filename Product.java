
package javafxapplication3;

public class Product {
    private String name;
    private double price;
    private String description;
    private int stock;
    private Category category;

    public Product(String name, double price, String description, int stock, Category category) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.stock = stock;
    this.category = category;
}


    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
