package model;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class ProductUtils {

    public static List<Product> filterByName(String name, List<Product> products) {
        return products.stream().filter(product -> product.getName().contains(name)).collect(Collectors.toList());
    }

    public static List<Product> filterById(ObjectId id, List<Product> products) {
        return products.stream().filter(product -> product.getId().equals(id)).collect(Collectors.toList());
    }

    public static List<Product> filterByType(String type, List<Product> products) {
        return products.stream().filter(product -> product.getType().contains(type)).collect(Collectors.toList());
    }

    public static List<Product> filterByQuantity(String quantity, List<Product> products) {
        return products.stream().filter(product -> product.getQuantity().contains(quantity)).collect(Collectors.toList());
    }
}
