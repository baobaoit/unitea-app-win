package com.hesmantech.uniteaappwin.model.print;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PagePrinter implements Printable {
  // calculates this by programming, not hard code at here.
  public static final int LINE_PER_PAGE = 60;
  private List<Object> pageContents;

  public PagePrinter() {
    pageContents = new ArrayList<>();
  }

  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
    Iterator<Object> iterator = pageContents.iterator();
    int previousY = 0;
    while (iterator.hasNext()) {
      InvoiceLine pe = (InvoiceLine) iterator.next();
      FontMetrics fontMetrics = graphics.getFontMetrics(pe.getFont());
      int lineHeight = fontMetrics.getHeight();

      if (previousY != 0) {
        pe.setY(previousY + lineHeight);
      }
      previousY = pe.getY();

      pe.print(graphics);
    }
    return PAGE_EXISTS;
  }

  public void addPrintElement(InvoiceLine pe) {
    pageContents.add(pe);
  }
}
