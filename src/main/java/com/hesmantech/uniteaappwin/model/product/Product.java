package com.hesmantech.uniteaappwin.model.product;

import java.util.List;

import com.hesmantech.uniteaappwin.model.product.attribute.Attribute;

public class Product {
  private long orderedQuantity;
  private String productName;
  private String subTotal;
  private List<Attribute> attributes;
  private List<Attribute> toppings;
  private List<Attribute> noneToppings;

  public String getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(String subTotal) {
    this.subTotal = subTotal;
  }

  public long getOrderedQuantity() {
    return orderedQuantity;
  }

  public void setOrderedQuantity(long orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public List<Attribute> getToppings() {
    return toppings;
  }

  public void setToppings(List<Attribute> toppings) {
    this.toppings = toppings;
  }

  public List<Attribute> getNoneToppings() {
    return noneToppings;
  }

  public void setNoneToppings(List<Attribute> noneToppings) {
    this.noneToppings = noneToppings;
  }
}
