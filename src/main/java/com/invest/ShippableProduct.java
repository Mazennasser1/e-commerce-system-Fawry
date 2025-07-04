package com.invest;

public class ShippableProduct extends Product implements Shippable{
    private double weight;

    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public String getname() {
        return getName();
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "ShippableProduct{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", weight=" + weight +
                '}';
    }
}
