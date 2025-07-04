package com.invest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        if (items == null || items.isEmpty()) return;

        System.out.println("** Shipment notice **");

        Map<String, Double> productWeights = new HashMap<>();
        Map<String, Integer> productCounts = new HashMap<>();
        double totalWeight = 0;

        for (Shippable item : items) {
            String name = item.getname();
            double weight = item.getWeight();

            productWeights.put(name, productWeights.getOrDefault(name, 0.0) + weight);
            productCounts.put(name, productCounts.getOrDefault(name, 0) + 1);

            totalWeight += weight;
        }

        for (String name : productCounts.keySet()) {
            double totalWeightForProduct = productWeights.get(name);
            int count = productCounts.get(name);

            System.out.printf("%dx %-12s %.0fg%n", count, name, totalWeightForProduct * 1000);
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}
