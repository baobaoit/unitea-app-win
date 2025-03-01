package com.hesmantech.uniteaappwin.utils;

import java.util.ArrayList;
import java.util.List;

import com.hesmantech.uniteaappwin.model.product.Product;
import com.hesmantech.uniteaappwin.model.product.attribute.Attribute;

public class ProductUtils {

  private static final String TOPPING = "Topping";

  private ProductUtils() {}

  public static void generateProductToppings(Product product) {
    List<Attribute> productToppings = new ArrayList<>();
    List<Attribute> productAttributes = product.getAttributes();

    if (productAttributes != null) {
      productAttributes.stream()
          .filter(attribute -> TOPPING.equalsIgnoreCase(attribute.getAttributeName()))
          .forEach(productToppings::add);
    }

    product.setToppings(productToppings);
  }

  public static void generateProductNoneToppings(Product product) {
    List<Attribute> productNoneToppings = new ArrayList<>();
    List<Attribute> productAttributes = product.getAttributes();

    if (productAttributes != null) {
      productAttributes.stream()
          .filter(attribute -> !TOPPING.equalsIgnoreCase(attribute.getAttributeName()))
          .forEach(productNoneToppings::add);
    }

    product.setNoneToppings(productNoneToppings);
  }
}
