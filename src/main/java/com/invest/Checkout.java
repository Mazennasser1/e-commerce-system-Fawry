package com.invest;
import java.util.ArrayList;
import java.util.List;

public class Checkout {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        double subtotal = 0;
        List<Shippable> shippableItems = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();

            if (p.isExpired()) throw new IllegalStateException(p.getName() + " is expired");
            if (item.getQuantity() > p.getQuantity()) throw new IllegalStateException(p.getName() + " is out of stock");

            subtotal += p.getPrice() * item.getQuantity();

            if (p instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) p);
                }
            }

            p.decreaseQuantity(item.getQuantity());
        }

        double shipping = shippableItems.isEmpty() ? 0 : 30;
        double total = subtotal + shipping;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance");
        }

        ShippingService.ship(shippableItems);
        customer.deductBalance(total);

        System.out.println("\n** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %-12s %.0f%n", item.getQuantity(), item.getProduct().getName(), item.getProduct().getPrice() * item.getQuantity());
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal         %.0f%n", subtotal);
        System.out.printf("Shipping         %.0f%n", shipping);
        System.out.printf("Amount           %.0f%n", total);
        System.out.printf("Balance left     %.0f%n", customer.getBalance());

        cart.clear();
    }
}
