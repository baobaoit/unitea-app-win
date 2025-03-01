package com.hesmantech.uniteaappwin.model.order.total.module;

public enum ModuleType {
  TIP("tip", "Tip"),
  TAX("tax", "Tax"),
  TOTAL("total", "Total"),
  CONVENIENCEFEE("conveniencefee", "Convenience fee"),
  SUBTOTAL("subtotal", "Subtotal");

  private final String value;
  private final String displayValue;

  ModuleType(String value, String displayValue) {
    this.value = value;
    this.displayValue = displayValue;
  }

  public String value() {
    return value;
  }

  public String displayValue() {
    return displayValue;
  }
}
