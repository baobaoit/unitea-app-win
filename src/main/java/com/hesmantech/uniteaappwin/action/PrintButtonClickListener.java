package com.hesmantech.uniteaappwin.action;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JOptionPane;

import com.hesmantech.uniteaappwin.constant.FontConstants;
import com.hesmantech.uniteaappwin.manager.PrintDataManager;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.print.InvoiceLine;
import com.hesmantech.uniteaappwin.model.print.PagePrinter;
import com.hesmantech.uniteaappwin.model.receipt.item.ReceiptLineItem;
import com.hesmantech.uniteaappwin.ui.component.OrderPrintButton;
import com.hesmantech.uniteaappwin.utils.OrderUtils;

public class PrintButtonClickListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      PrinterJob printerJob = PrinterJob.getPrinterJob();
      //            PrintService printService = (PrintService)
      // PrintDataManager.datas.get(PrintService.class);
      //
      //            printerJob.setPrintService(printService);
      Book book = new Book();
      PageFormat pageFormat = printerJob.defaultPage(new PageFormat());
      Paper paper = new Paper();
      paper.setImageableArea(0, 10, pageFormat.getWidth(), pageFormat.getHeight());
      pageFormat.setPaper(paper);
      List<PagePrinter> pages = new ArrayList<>();

      int x = (int) pageFormat.getImageableX() + 12;
      int y = (int) pageFormat.getImageableY() + 12;
      AtomicInteger countPrintElement = new AtomicInteger(0);
      AtomicReference<PagePrinter> page = new AtomicReference<>(new PagePrinter());
      List<ReceiptLineItem> receiptLineContent;
      if (e.getSource() instanceof OrderPrintButton) {
        receiptLineContent = OrderUtils.getOrderLineContents(PrintDataManager.get(Order.class));
      } else {
        receiptLineContent = OrderUtils.getTestPrinterLineContents();
      }

      receiptLineContent.forEach(
          lineItem -> {
            if (countPrintElement.get() > PagePrinter.LINE_PER_PAGE) {
              countPrintElement.set(0);
              page.set(new PagePrinter());
            }
            PagePrinter pageGet = page.get();
            Font font =
                new Font(
                    FontConstants.SANS_SERIF_FONT_NAME,
                    lineItem.getPlainStyle(),
                    lineItem.getFontSize());

            pageGet.addPrintElement(new InvoiceLine(lineItem.getText(), font, x, y));
            if (!pages.contains(pageGet)) {
              pages.add(pageGet);
            }

            // will be overflowed if font size is larger than remain height of page
            if (lineItem.getFontSize() > 9) {
              int offset = (int) Math.ceil(lineItem.getFontSize() / 9);
              countPrintElement.set(countPrintElement.get() + offset);
            } else {
              countPrintElement.getAndIncrement();
            }
          });

      pages.forEach(pg -> book.append(pg, pageFormat));
      printerJob.setPageable(book);
      printerJob.print();

    } catch (PrinterException printException) {
      if (printException.getMessage().equals("Service cannot be null")) {
        JOptionPane.showMessageDialog(null, "Your device is not connecting with any printer.");
      } else {
        JOptionPane.showMessageDialog(null, printException.getMessage());
      }
      printException.printStackTrace();
    }
  }
}
