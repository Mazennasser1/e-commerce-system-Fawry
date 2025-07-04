package com.invest;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<Product> products = ProductDatabase.loadProducts("products.json");

        Customer customer = new Customer("Mazen", 600);
        Cart cart = new Cart();

        cart.add(products.get(0), 2); // Cheese
        cart.add(products.get(0), 2); // Cheese

//        cart.add(products.get(1), 1); // Biscuits

//        cart.add(products.get(2), 1); // Scratch Card


        Checkout.checkout(customer, cart);

        ProductDatabase.saveProducts(products, "products.json");
    }
}
