package com.invest;

import java.time.LocalDate;

public class ExpirableProductWithShipping extends ShippableProduct {
    private LocalDate expiryDate;

    public ExpirableProductWithShipping(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, weight);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}
