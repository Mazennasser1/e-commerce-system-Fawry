package com.invest;

public class Customer {
    String name;
    double balance;

    public Customer(String name, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.name = name;
        this.balance = balance;
    }
    public String getName() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public void deductBalance(double amount) {
        this.balance -= amount;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
