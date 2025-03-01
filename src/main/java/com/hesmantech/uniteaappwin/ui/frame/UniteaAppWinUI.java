package com.hesmantech.uniteaappwin.ui.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.hesmantech.uniteaappwin.constant.Colors;
import com.hesmantech.uniteaappwin.constant.FontConstants;
import com.hesmantech.uniteaappwin.gateway.UniteaGateway;
import com.hesmantech.uniteaappwin.manager.BeanManager;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.order.ResponseOrderList;
import com.hesmantech.uniteaappwin.ui.panel.OrderDetailPanel;
import com.hesmantech.uniteaappwin.ui.panel.OrderPanel;
import com.hesmantech.uniteaappwin.ui.panel.SettingPanel;

public class UniteaAppWinUI extends JFrame {

  private static final long serialVersionUID = 8817217270728280635L;

  private UniteaGateway uniteaGateway;

  private JPanel contentPane;
  private JPanel pnlLeft;
  private JPanel pnlRight;
  private JPanel pnlFirstOrder;

  /** Create the frame. */
  public UniteaAppWinUI() {
    setTitle("Hesmantech - Unitea");
    setExtendedState(Frame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    contentPane = new JPanel(new GridBagLayout());
    setContentPane(contentPane);

    this.uniteaGateway = BeanManager.getUniteaGateway();

    Dimension screenSize = getToolkit().getScreenSize();
    int screenSizeWidth = screenSize.width;
    int screenSizeHeight = screenSize.height;
    setSize(screenSize);

    pnlLeft = initLeftData();
    Dimension pnlLeftDimension = new Dimension((int) (screenSizeWidth * 0.25), screenSizeHeight);
    pnlLeft.setSize(pnlLeftDimension);
    pnlLeft.setMinimumSize(pnlLeftDimension);
    pnlLeft.setPreferredSize(pnlLeftDimension);
    GridBagConstraints gbcPnlLeft = new GridBagConstraints();
    gbcPnlLeft.weighty = 1.0;
    gbcPnlLeft.weightx = 0.25;
    gbcPnlLeft.fill = GridBagConstraints.BOTH;
    gbcPnlLeft.gridx = 0;
    gbcPnlLeft.gridy = 0;
    contentPane.add(pnlLeft, gbcPnlLeft);

    pnlRight = new JPanel();
    Dimension pnlRightDimension = new Dimension((int) (screenSizeWidth * 0.75), screenSizeHeight);
    pnlRight.setSize(pnlRightDimension);
    pnlRight.setMinimumSize(pnlRightDimension);
    pnlRight.setPreferredSize(pnlRightDimension);
    GridBagConstraints gbcPnlRight = new GridBagConstraints();
    gbcPnlRight.weightx = 0.75;
    gbcPnlRight.weighty = 1.0;
    gbcPnlRight.fill = GridBagConstraints.BOTH;
    gbcPnlRight.gridx = 0;
    gbcPnlRight.gridy = 0;
    contentPane.add(pnlRight, gbcPnlRight);
    GridBagLayout gblPnlRight = new GridBagLayout();
    pnlRight.setLayout(gblPnlRight);
  }

  private JPanel initLeftData() {
    JPanel pnlLeft = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    pnlLeft.add(initLeftDataDisplay(), gbc);

    return pnlLeft;
  }

  private JComponent initLeftDataDisplay() {
    JPanel pnlLeftData = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    pnlLeftData.add(initOrderListPanel(), gbc);

    return pnlLeftData;
  }

  private JComponent initOrderListPanel() {
    List<Order> orderList = getOngoingOrder();

    JTabbedPane tabbedPane = new JTabbedPane();
    Font tabTitleFont = new Font(FontConstants.SANS_SERIF_FONT_NAME, Font.PLAIN, 18);
    tabbedPane.setFont(tabTitleFont);
    tabbedPane.setOpaque(true);
    tabbedPane.setBackground(Colors.TABBED_PANE_BACKGROUND_COLOR);

    JScrollPane scrollPane = new JScrollPane();

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Colors.TABBED_PANE_PANEL_BACKGROUD_COLOR);
    JLabel lblCompleted = new JLabel("Completed");
    lblCompleted.setFont(FontConstants.DEFAULT_FONT_27);
    lblCompleted.setForeground(Color.WHITE);
    panel.add(lblCompleted);

    for (int i = 0; i < orderList.size(); i++) {

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;

      JPanel pnlOrder = new OrderPanel(orderList.get(i));

      gbc.insets = new Insets(2, 10, 2, 10);
      gbc.gridx = 0;
      gbc.gridy = i + 1;
      gbc.weightx = 1.0;
      gbc.ipady = 40;

      if (i == 0) {
        pnlFirstOrder = pnlOrder;
      }

      panel.add(pnlOrder, gbc);
    }

    scrollPane.setViewportView(panel);

    tabbedPane.addTab("Orders", scrollPane);
    tabbedPane.addTab("Settings", new SettingPanel());

    return tabbedPane;
  }

  public void displayOrderDetails(OrderDetailPanel orderDetailPanel) {
    contentPane.remove(pnlRight);
    pnlRight = orderDetailPanel;

    GridBagConstraints gbcPnlRight = new GridBagConstraints();
    gbcPnlRight.fill = GridBagConstraints.BOTH;
    gbcPnlRight.weightx = 1;
    gbcPnlRight.gridx = 1;
    gbcPnlRight.gridy = 0;

    contentPane.add(pnlRight, gbcPnlRight);
    revalidate();
    repaint();
  }

  private List<Order> getOngoingOrder() {
    ResponseOrderList responseOrderList = uniteaGateway.getOngoingOrders();

    if (responseOrderList != null && responseOrderList.getOrders() != null) {
      return responseOrderList.getOrders();
    }

    return Collections.emptyList();
  }

  public JPanel getPnlFirstOrder() {
    return pnlFirstOrder;
  }
}
