package com.hesmantech.uniteaappwin.model.receipt.item;

import java.awt.Font;

public class ReceiptLineItem {
  private String text;
  private int plainStyle = Font.PLAIN;
  private int fontSize = 9;

  public ReceiptLineItem(String text, int plainStyle, int fontSize) {
    this.text = text;
    this.plainStyle = plainStyle;
    this.fontSize = fontSize;
  }

  public int getFontSize() {
    return fontSize;
  }

  public ReceiptLineItem(String text, int plainStyle) {
    this.text = text;
    this.plainStyle = plainStyle;
  }

  public ReceiptLineItem(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getPlainStyle() {
    return plainStyle;
  }

  public void setPlainStyle(int plainStyle) {
    this.plainStyle = plainStyle;
  }
}
