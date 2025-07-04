package com.invest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    public static List<Product> loadProducts(String path) {
        List<Product> productList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ProductData> rawList = mapper.readValue(
                    new File(path),
                    new TypeReference<List<ProductData>>() {}
            );

            for (ProductData data : rawList) {
                switch (data.type) {
                    case "shippable":
                        if(data.expiryDate != null) {
                            LocalDate expiryDate = LocalDate.parse(data.expiryDate);
                            ExpirableProcduct expirableProduct = new ExpirableProcduct(data.name, data.price, data.quantity, expiryDate);
                            productList.add(expirableProduct);
                        }
                        Product p1 = new ShippableProduct(data.name, data.price, data.quantity, data.weight);

                        productList.add(p1);
                        break;

                    case "nonExpirable":
                        productList.add(new Product(data.name, data.price, data.quantity));
                        break;

                    default:
                        throw new RuntimeException("Unknown product type: " + data.type);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static void saveProducts(List<Product> products, String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<ProductData> dataList = new ArrayList<>();
        for (Product product : products) {
            ProductData data = new ProductData();
            if (product instanceof ShippableProduct) {
                data.type = "shippable";
                data.weight = ((ShippableProduct) product).getWeight();
            } else {
                data.type = "nonExpirable";
                data.weight = null;
            }
            data.name = product.getName();
            data.price = product.getPrice();
            data.quantity = product.getQuantity();
            // If you add expiryDate logic, set here
            data.expiryDate = null;
            dataList.add(data);
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

