package com.hesmantech.uniteaappwin.model.order;

import java.util.List;

import com.hesmantech.uniteaappwin.model.billing.Billing;
import com.hesmantech.uniteaappwin.model.order.total.OrderTotal;
import com.hesmantech.uniteaappwin.model.product.Product;

public class Order {
  private long id;
  private String invoiceDatePurchased;
  private Billing billing;
  private List<Product> products;
  private List<OrderTotal> totals;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getInvoiceDatePurchased() {
    return invoiceDatePurchased;
  }

  public void setInvoiceDatePurchased(String invoiceDatePurchased) {
    this.invoiceDatePurchased = invoiceDatePurchased;
  }

  public Billing getBilling() {
    return billing;
  }

  public void setBilling(Billing billing) {
    this.billing = billing;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public List<OrderTotal> getTotals() {
    return totals;
  }

  public void setTotals(List<OrderTotal> totals) {
    this.totals = totals;
  }
}
