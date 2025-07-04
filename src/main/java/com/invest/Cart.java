package com.invest;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private List <Shippable> shippableItems;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (product instanceof Shippable) {
            if (shippableItems == null) {
                shippableItems = new ArrayList<>();
            }
            shippableItems.add((Shippable) product);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if(quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Cannot add " + quantity + " of " + product.getName() +
                    ". Only " + product.getQuantity() + " available.");
        }
        if (product.isExpired()) {
            throw new IllegalStateException("Cannot add expired product: " + product.getName());
        }
        CartItem item = new CartItem(product, quantity);
        items.add(item);
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public double getTotalPrice() {
        double total=0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
    public void clear() {
        items.clear();
        if (shippableItems != null) {
            shippableItems.clear();
        }
    }
}
