package com.hesmantech.uniteaappwin.ui.panel;

import static com.hesmantech.uniteaappwin.utils.OrderUtils.PRODUCT_ATTRIBUTE_MILK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.hesmantech.uniteaappwin.Constants;
import com.hesmantech.uniteaappwin.constant.Colors;
import com.hesmantech.uniteaappwin.constant.DateTimeFormatConstants;
import com.hesmantech.uniteaappwin.constant.FontConstants;
import com.hesmantech.uniteaappwin.manager.ComponentManager;
import com.hesmantech.uniteaappwin.manager.PrintDataManager;
import com.hesmantech.uniteaappwin.model.billing.Billing;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.order.total.OrderTotal;
import com.hesmantech.uniteaappwin.model.order.total.module.ModuleType;
import com.hesmantech.uniteaappwin.model.product.Product;
import com.hesmantech.uniteaappwin.model.product.attribute.Attribute;
import com.hesmantech.uniteaappwin.ui.component.OrderPrintButton;
import com.hesmantech.uniteaappwin.utils.DateTimeUtils;
import com.hesmantech.uniteaappwin.utils.OrderUtils;;

public class OrderDetailPanel extends JPanel {
  private static final long serialVersionUID = -5002459553888175550L;
  private static final String PRODUCT_ATTRIBUTE_HOT_OR_COLD = "Hot or Cold";
  private static final String SPACE = " ";

  {
    setLayout(new GridBagLayout());
  }

  public OrderDetailPanel() {
    Order order = PrintDataManager.get(Order.class);
    JScrollPane scrollPane = new JScrollPane();

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(ComponentManager.getPadding(ComponentManager.LEFT_RIGHT, 10));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    panel.add(initHeader(order), gbc);
    gbc.anchor = GridBagConstraints.FIRST_LINE_END;
    panel.add(initReceiptDetail(order), gbc);

    gbc.gridy = 0;
    scrollPane.setViewportView(panel);
    gbc.fill = GridBagConstraints.BOTH;
    add(scrollPane, gbc);

    // init mouse dragging event
    MouseAdapter adapter =
        new MouseAdapter() {

          private Point origin;

          @Override
          public void mousePressed(MouseEvent e) {
            origin = new Point(e.getPoint());
          }

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseDragged(MouseEvent e) {
            if (origin != null) {
              JViewport viewPort =
                  (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, panel);
              if (viewPort != null) {
                int deltaY = origin.y - e.getY();

                Rectangle view = viewPort.getViewRect();
                view.y += deltaY;

                panel.scrollRectToVisible(view);
              }
            }
          }
        };

    panel.addMouseListener(adapter);
    panel.addMouseMotionListener(adapter);
  }

  private JComponent initReceiptDetail(Order order) {
    JPanel pnlOrderDetails = new JPanel(new GridBagLayout());
    pnlOrderDetails.setBackground(Color.WHITE);

    // header
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weightx = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 1;

    pnlOrderDetails.add(initHeader(order), gbc);
    gbc.gridy++;
    // body
    if (order.getProducts() != null) {

      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(5, 2, 5, 2);

      for (Product product : order.getProducts()) {

        GridBagConstraints gbcProduct = new GridBagConstraints();
        gbcProduct.gridx = 0;
        gbcProduct.gridy = 0;
        gbcProduct.weighty = 1;
        gbcProduct.weightx = 1;
        gbcProduct.fill = GridBagConstraints.HORIZONTAL;
        gbcProduct.insets = new Insets(5, 20, 5, 20);

        JPanel pnlProduct = new RoundedPanel(new GridBagLayout(), 25);
        pnlProduct.setBackground(Color.WHITE);
        ((RoundedPanel) pnlProduct).setEnabledBorder(true);
        ((RoundedPanel) pnlProduct).setBorderColor(Color.LIGHT_GRAY);

        JPanel pnlProductHeader = new JPanel(new BorderLayout());
        pnlProductHeader.setBorder(ComponentManager.getPadding(ComponentManager.TOP, 15));
        pnlProductHeader.setBackground(Color.WHITE);
        JLabel lblProductName = new JLabel(product.getProductName());
        lblProductName.setFont(FontConstants.DEFAULT_FONT_BOLD_27);
        JLabel lblSubTotal = new JLabel(product.getSubTotal());
        lblSubTotal.setFont(FontConstants.DEFAULT_FONT_27);

        pnlProductHeader.add(lblProductName, BorderLayout.WEST);
        pnlProductHeader.add(lblSubTotal, BorderLayout.EAST);

        pnlProduct.add(pnlProductHeader, gbcProduct);
        gbcProduct.gridy++;

        // attribute
        List<Attribute> productAttributes = product.getAttributes();
        if (productAttributes != null) {
          Set<String> attributeNames =
              productAttributes.stream()
                  .map(Attribute::getAttributeName)
                  .collect(Collectors.toSet());
          long skipStep = attributeNames.size() - 1;
          String lastAttributeName = "";
          if (skipStep > 0) {
            lastAttributeName = attributeNames.stream().skip(skipStep).findFirst().orElse("N/A");
          }

          for (String name : attributeNames) {

            if (!PRODUCT_ATTRIBUTE_MILK.equalsIgnoreCase(name)
                && !PRODUCT_ATTRIBUTE_HOT_OR_COLD.equalsIgnoreCase(name)) {
              JLabel lblAttribute = new JLabel(name);
              lblAttribute.setFont(FontConstants.DEFAULT_FONT);
              pnlProduct.add(lblAttribute, gbcProduct);
              gbcProduct.gridy++;
            }

            ListIterator<Attribute> iterator = productAttributes.listIterator();
            while (iterator.hasNext()) {
              Attribute attribute = iterator.next();
              String attributeName = attribute.getAttributeName();
              String attributeValue = attribute.getAttributeValue();
              JLabel lblAttribute = new JLabel();
              lblAttribute.setFont(FontConstants.DEFAULT_FONT);
              if (name.equalsIgnoreCase(attributeName)) {
                if (PRODUCT_ATTRIBUTE_MILK.equalsIgnoreCase(name)) {
                  lblAttribute.setText(
                      new StringBuilder(OrderUtils.convertToReableAttributeValue(attributeValue))
                          .append(attributeName)
                          .toString());
                } else if (PRODUCT_ATTRIBUTE_HOT_OR_COLD.equalsIgnoreCase(name)) {
                  lblAttribute.setText(attributeValue);
                } else {
                  lblAttribute.setText("1x " + attributeValue);
                }

                if (lastAttributeName.equalsIgnoreCase(name)) {
                  lblAttribute.setBorder(ComponentManager.getPadding(ComponentManager.BOTTOM, 15));
                }

                pnlProduct.add(lblAttribute, gbcProduct);
                gbcProduct.gridy++;
              }
            }
          }
        }
        gbcProduct.gridy += 2;

        pnlOrderDetails.add(pnlProduct, gbc);
        gbc.gridy++;
      }

      JPanel pnlLine = new JPanel(new BorderLayout());
      pnlLine.setBorder(BorderFactory.createLineBorder(Colors.SEPARATE_LINE_COLOR, 1));
      pnlLine.setOpaque(true);
      GridBagConstraints gbcPnlLine = new GridBagConstraints();
      gbcPnlLine.gridy = gbc.gridy;
      gbcPnlLine.fill = GridBagConstraints.HORIZONTAL;
      pnlOrderDetails.add(pnlLine, gbcPnlLine);
      gbc.gridy++;

      // total
      if (order.getTotals() != null) {

        JPanel pnlTotals = new JPanel(new GridBagLayout());
        pnlTotals.setBackground(Color.WHITE);

        GridBagConstraints gbcPnlTotal = new GridBagConstraints();
        gbcPnlTotal.weightx = 1;
        gbcPnlTotal.gridx = 0;
        gbcPnlTotal.gridy = 0;
        gbcPnlTotal.weighty = 1;
        gbcPnlTotal.fill = GridBagConstraints.HORIZONTAL;

        for (OrderTotal orderTotal : order.getTotals()) {
          JPanel pnlTotal = new JPanel(new BorderLayout());
          pnlTotal.setBackground(Color.WHITE);

          String orderTotalModule = orderTotal.getModule();
          ModuleType moduleType = ModuleType.valueOf(orderTotalModule.toUpperCase());
          JLabel lblTotalName = new JLabel(moduleType.displayValue());
          lblTotalName.setFont(FontConstants.DEFAULT_FONT);
          JLabel lblTotalValue = new JLabel("$" + orderTotal.getValue());
          lblTotalValue.setFont(FontConstants.DEFAULT_FONT);

          pnlTotal.add(lblTotalName, BorderLayout.WEST);
          pnlTotal.add(lblTotalValue, BorderLayout.EAST);

          pnlTotals.add(pnlTotal, gbc);
          gbc.gridy++;
        }

        pnlOrderDetails.add(pnlTotals, gbc);
        gbc.gridy++;
      }

      gbc.fill = GridBagConstraints.HORIZONTAL;

      pnlOrderDetails.add(
          new OrderPrintButton(
              new ImageIcon(
                  Objects.requireNonNull(getClass().getResource(Constants.PRINTER_BUTTON_ICON)))),
          gbc);

      return pnlOrderDetails;
    }

    return new JPanel();
  }

  private JComponent initHeader(Order order) {
    String date =
        DateTimeUtils.formatInvoiceDatePurchased(
            order.getInvoiceDatePurchased(),
            DateTimeFormatConstants.UNITEA_ORDER_DETAIL_DATE_FORMAT);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);

    JPanel pnlPickupDate = new JPanel(new GridBagLayout());
    pnlPickupDate.setBackground(Color.WHITE);
    JLabel lblPickup = new JLabel("Pickup ");
    lblPickup.setFont(FontConstants.DEFAULT_FONT_BOLD_27);
    pnlPickupDate.add(lblPickup);

    JLabel lblPickupDate = new JLabel("- " + date);
    lblPickupDate.setFont(FontConstants.DEFAULT_FONT_27);
    GridBagConstraints gbcLblPickupDate = new GridBagConstraints();
    gbcLblPickupDate.gridx = 1;
    pnlPickupDate.add(lblPickupDate, gbcLblPickupDate);

    Billing billing = order.getBilling();
    JLabel lblCustomerFullName =
        new JLabel(
            new StringBuilder(billing.getFirstName())
                .append(SPACE)
                .append(billing.getLastName())
                .toString());
    lblCustomerFullName.setFont(FontConstants.DEFAULT_FONT);
    JLabel lblOrderId = new JLabel(new StringBuilder(" #").append(order.getId()).toString());
    lblOrderId.setFont(FontConstants.DEFAULT_FONT);
    lblOrderId.setForeground(Colors.ORDER_ID_FOREGROUND_COLOR);
    GridBagConstraints gbcLblOrderId = new GridBagConstraints();
    gbcLblOrderId.gridx = 1;
    gbcLblOrderId.gridy = 0;

    JLabel lblPhone = new JLabel(billing.getPhone());
    lblPhone.setFont(FontConstants.DEFAULT_FONT);

    JPanel pnlTopRight = new JPanel(new GridBagLayout());
    pnlTopRight.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    pnlTopRight.add(lblCustomerFullName, gbc);
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.LAST_LINE_END;
    gbc.gridwidth = 2;
    pnlTopRight.add(lblPhone, gbc);
    pnlTopRight.add(lblOrderId, gbcLblOrderId);

    panel.add(pnlPickupDate, BorderLayout.WEST);
    panel.add(pnlTopRight, BorderLayout.EAST);

    return panel;
  }
}
