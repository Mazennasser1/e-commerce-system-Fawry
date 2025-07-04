package com.invest;

import java.util.HashSet;
import java.util.Set;

import static java.lang.System.exit;

public class Product {
    private static final Set<Product> products = new HashSet<>();
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;

        if (!products.add(this)) {
            System.err.println("Duplicate product detected: " + name);
            System.err.println(" Application terminated due to duplicate product.");
            System.exit(1);
        }
    }

    public boolean isExpired() {
        return false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void decreaseQuantity(int amount) {
        if (amount > 0 && quantity >= amount) {
            quantity -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount to decrease quantity");
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
