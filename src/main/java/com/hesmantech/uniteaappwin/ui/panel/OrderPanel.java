package com.hesmantech.uniteaappwin.ui.panel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.hesmantech.uniteaappwin.constant.FontConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hesmantech.uniteaappwin.constant.Colors;
import com.hesmantech.uniteaappwin.constant.DateTimeFormatConstants;
import com.hesmantech.uniteaappwin.manager.ComponentManager;
import com.hesmantech.uniteaappwin.manager.EventManager;
import com.hesmantech.uniteaappwin.model.billing.Billing;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.utils.DateTimeUtils;

public class OrderPanel extends RoundedPanel implements MouseListener {

  private static final long serialVersionUID = 8174554465875297133L;
  private static final Logger log = LoggerFactory.getLogger(OrderPanel.class);
  private static final int DEFAULT_ORDER_PANEL_CORNER_RADIUS = 15;

  private Long orderId;

  public OrderPanel(Order order) {
    super(new BorderLayout(), DEFAULT_ORDER_PANEL_CORNER_RADIUS);
    setBackground(Colors.ORDER_PANEL_BACKGROUND_COLOR);
    this.orderId = order.getId();
    Billing billing = order.getBilling();
    String invoiceDatePurchased = order.getInvoiceDatePurchased();
    String time =
        DateTimeUtils.formatInvoiceDatePurchased(
            invoiceDatePurchased, DateTimeFormatConstants.UNITEA_APP_WIN_ORDER_TIME_FORMAT);

    JLabel lblLeft =
        new JLabel(
            new StringBuilder(billing.getFirstName())
                .append(" ")
                .append(billing.getLastName())
                .toString());
    lblLeft.setForeground(Colors.ORDER_PANEL_CONTENT_COLOR);
    lblLeft.setBorder(ComponentManager.getPadding(ComponentManager.LEFT, 10));
    lblLeft.setFont(FontConstants.DEFAULT_FONT);

    JLabel lblRight = new JLabel(time);
    lblRight.setBorder(ComponentManager.getPadding(ComponentManager.RIGHT, 10));
    lblRight.setForeground(Colors.ORDER_PANEL_CONTENT_COLOR);
    lblRight.setFont(FontConstants.DEFAULT_FONT);

    add(lblLeft, BorderLayout.WEST);
    add(lblRight, BorderLayout.EAST);
    addMouseListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent event) {
    log.info("Get order details of order id: {}", orderId);
    EventManager.showOrderDetails(this);
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  public Long getOrderId() {
    return orderId;
  }
}
