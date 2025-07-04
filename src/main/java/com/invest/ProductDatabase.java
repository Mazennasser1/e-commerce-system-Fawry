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
                            ExpirableProductWithShipping expirableProduct = new ExpirableProductWithShipping(data.name, data.price, data.quantity, expiryDate, data.weight);
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

    public static void saveProducts(List<Product> updatedProducts, String path) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ProductData> originalDataList = mapper.readValue(new File(path), new TypeReference<List<ProductData>>() {});

            for (Product updatedProduct : updatedProducts) {
                for (ProductData data : originalDataList) {
                    boolean sameName = data.name.equals(updatedProduct.getName());
                    boolean sameWeight = (data.weight == null && !(updatedProduct instanceof Shippable))
                            || (data.weight != null && updatedProduct instanceof Shippable && data.weight.equals(((Shippable) updatedProduct).getWeight()));

                    boolean sameExpiry = true;
                    if (data.expiryDate != null && updatedProduct instanceof ExpirableProductWithShipping e) {
                        sameExpiry = data.expiryDate.equals(e.getExpiryDate().toString());
                    } else if (data.expiryDate != null) {
                        sameExpiry = false;
                    }

                    if (sameName && sameWeight && sameExpiry) {
                        data.quantity = updatedProduct.getQuantity();
                        break;
                    }
                }
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), originalDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

