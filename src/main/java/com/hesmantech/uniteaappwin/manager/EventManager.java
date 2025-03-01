package com.hesmantech.uniteaappwin.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.ui.frame.UniteaAppWinUI;
import com.hesmantech.uniteaappwin.ui.panel.OrderDetailPanel;
import com.hesmantech.uniteaappwin.ui.panel.OrderPanel;

public final class EventManager {
  private static JFrame frmMain;

  private EventManager() {}

  public static void showOrderDetails(JPanel panel) {
    if (panel instanceof OrderPanel && frmMain instanceof UniteaAppWinUI) {
      OrderPanel pnlOrder = (OrderPanel) panel;
      UniteaAppWinUI mainUI = (UniteaAppWinUI) frmMain;
      Order order = BeanManager.getUniteaGateway().getOrderDetailsById(pnlOrder.getOrderId());
      PrintDataManager.put(Order.class, order);

      OrderDetailPanel orderDetailPanel = new OrderDetailPanel();
      mainUI.displayOrderDetails(orderDetailPanel);
      ComponentManager.toggleActiveOrderPanel(pnlOrder);
    }
  }

  public static void showFirstOrderDetails() {
    if (frmMain instanceof UniteaAppWinUI) {
      UniteaAppWinUI mainUI = (UniteaAppWinUI) frmMain;
      showOrderDetails(mainUI.getPnlFirstOrder());
    }
  }

  public static void setFrmMain(JFrame frmMain) {
    EventManager.frmMain = frmMain;
  }
}
