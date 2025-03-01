package com.hesmantech.uniteaappwin.model.print;

import java.awt.Font;
import java.awt.Graphics;

public class InvoiceLine {
  private String text;
  private Font font;
  private int x, y;

  public InvoiceLine(String text, Font font, int x, int y) {
    this.text = text;
    this.font = font;
    this.x = x;
    this.y = y;
  }

  public void print(Graphics g) {
    Font oldFont = g.getFont();
    g.setFont(font);
    g.drawString(text, x, y);
    g.setFont(oldFont);
  }

  public Font getFont() {
    return font;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
