package com.invest;

import java.time.LocalDate;

public class ExpirableProcduct extends Product {
    private LocalDate expirationDate;

    public ExpirableProcduct(String name, double price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        LocalDate expiryDate;
        return expirationDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "ExpirableProcduct{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
