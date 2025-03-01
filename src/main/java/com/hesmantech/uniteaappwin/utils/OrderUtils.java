package com.hesmantech.uniteaappwin.utils;

import static java.awt.Font.BOLD;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.PrintService;

import com.hesmantech.uniteaappwin.manager.PrintDataManager;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.order.total.OrderTotal;
import com.hesmantech.uniteaappwin.model.order.total.module.ModuleType;
import com.hesmantech.uniteaappwin.model.product.Product;
import com.hesmantech.uniteaappwin.model.product.attribute.Attribute;
import com.hesmantech.uniteaappwin.model.receipt.item.ReceiptLineItem;

public class OrderUtils {
  private static final String DASH_65 =
      "-----------------------------------------------------------------";
  private static final String SPACE_31 = "                               ";
  private static final String SPACE_12 = "            ";
  private static final String SPACE_15 = "               ";
  private static final String ASTERISK_54 =
      "******************************************************";
  private static final String BREAK_LINE = "\r\n";
  private static final String UNITEA_HEADER = "UNITEA";
  private static final String UNITEA_FOOTER = "THANK YOU FOR YOUR ORDER";
  private static final String SPACE = " ";
  private static final String OPTION_STRING_1X = "     - 1x ";
  private static final String OPTION_STRING = "     - ";
  private static final String ALREADY_PAID_STRING = "Already Paid";
  private static final String POWERED_BY_FOOTER = "POWERED BY HESMANTECH";

  private static final String DEFAULT_MESSAGE =
      "this is a test message from Hesmantech application.";
  
  public static final String PRODUCT_ATTRIBUTE_MILK = "Milk";
  public static final String PRODUCT_ATTRIBUTE_NO_MILK = "No";
  public static final String PRODUCT_ATTRIBUTE_YES_MILK = "";

  public static double getOrderModuleValue(Order order, ModuleType module) {
    List<OrderTotal> orderTotals = order.getTotals();
    if (orderTotals != null && module != null) {
      for (OrderTotal orderTotal : orderTotals) {
        if (module.value().equalsIgnoreCase(orderTotal.getModule())) {
          return orderTotal.getValue();
        }
      }
    }

    return 0;
  }

  public static List<ReceiptLineItem> getTestPrinterLineContents() {
    List<ReceiptLineItem> lineItems = new ArrayList<>();
    PrintService ps = PrintDataManager.get(PrintService.class);
    if (ps != null) {
      lineItems.add(new ReceiptLineItem(DEFAULT_MESSAGE));
      lineItems.add(new ReceiptLineItem("From printer :" + ps.getName()));
    }

    return lineItems;
  }

  public static List<ReceiptLineItem> getOrderLineContents(Order order) {
    List<ReceiptLineItem> lineItems = new ArrayList<>();
    if (order != null) {

      lineItems.add(new ReceiptLineItem(DASH_65));
      lineItems.add(new ReceiptLineItem(SPACE_31 + UNITEA_HEADER, BOLD));
      lineItems.add(new ReceiptLineItem(DASH_65));
      lineItems.add(new ReceiptLineItem(BREAK_LINE));
      lineItems.add(new ReceiptLineItem("To Go", BOLD));

      lineItems.add(
          new ReceiptLineItem(
              order.getBilling().getFirstName() + SPACE + order.getBilling().getLastName(),
              BOLD,
              12));
      lineItems.add(new ReceiptLineItem(BREAK_LINE));
      lineItems.add(new ReceiptLineItem("Order #" + order.getId()));
      lineItems.add(new ReceiptLineItem(order.getInvoiceDatePurchased()));

      if (order.getProducts().size() < 2) {
        lineItems.add(new ReceiptLineItem(order.getProducts().size() + " item", BOLD));
      } else {
        lineItems.add(new ReceiptLineItem(order.getProducts().size() + " items", BOLD));
      }
      lineItems.add(new ReceiptLineItem(BREAK_LINE));

      List<Product> products = order.getProducts();
      for (Product p : products) {
        ProductUtils.generateProductToppings(p);
        ProductUtils.generateProductNoneToppings(p);

        lineItems.add(
            new ReceiptLineItem(
                "   - " + p.getOrderedQuantity() + " x " + p.getProductName(), BOLD));

        // topping
        if (!p.getToppings().isEmpty()) {

          lineItems.add(new ReceiptLineItem("    Toppings:"));
          for (Attribute attr : p.getToppings()) {

            lineItems.add(new ReceiptLineItem(OPTION_STRING_1X + attr.getAttributeValue()));
          }
        }

        // non-topping
        if (!p.getNoneToppings().isEmpty()) {
          lineItems.add(new ReceiptLineItem("    Notes:"));
          for (Attribute attr : p.getNoneToppings()) {
            String attrName = attr.getAttributeName();
            String attrValue = attr.getAttributeValue();
            StringBuilder noneToppingBuilder = new StringBuilder(OPTION_STRING);
            if (PRODUCT_ATTRIBUTE_MILK.equalsIgnoreCase(attrName)) {
              noneToppingBuilder.append(convertToReableAttributeValue(attrValue)).append(attrName);
            } else {
              noneToppingBuilder.append(attrValue);
            }
            lineItems.add(new ReceiptLineItem(noneToppingBuilder.toString()));
          }
        }

        lineItems.add(new ReceiptLineItem(BREAK_LINE));
      }

      lineItems.add(new ReceiptLineItem(ALREADY_PAID_STRING));

      for (OrderTotal price : order.getTotals()) {
        String priceModule =
            Optional.ofNullable(price.getModule())
                .map(String::toUpperCase)
                .map(ModuleType::valueOf)
                .map(ModuleType::displayValue)
                .orElse("N/A");
        lineItems.add(new ReceiptLineItem(String.format("%s: $%s", priceModule, price.getValue())));
      }

      lineItems.add(new ReceiptLineItem(BREAK_LINE));
      lineItems.add(new ReceiptLineItem(SPACE_12 + UNITEA_FOOTER));
      lineItems.add(new ReceiptLineItem(ASTERISK_54));
      lineItems.add(new ReceiptLineItem(SPACE_15 + POWERED_BY_FOOTER));

      return lineItems;
    }

    return new ArrayList<>();
  }

  public static int getLineNumber(String receipt) {
    return Optional.ofNullable(receipt).map(str -> str.split(BREAK_LINE).length).orElse(0);
  }
  
  public static String convertToReableAttributeValue(String attributeValue) {
    return PRODUCT_ATTRIBUTE_NO_MILK.equalsIgnoreCase(attributeValue)
        ? attributeValue + SPACE
        : PRODUCT_ATTRIBUTE_YES_MILK;
  }
}
