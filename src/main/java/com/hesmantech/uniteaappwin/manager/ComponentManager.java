package com.hesmantech.uniteaappwin.manager;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hesmantech.uniteaappwin.constant.Colors;

public final class ComponentManager {
  public static final int TOP = 0;
  public static final int LEFT = 1;
  public static final int BOTTOM = 2;
  public static final int RIGHT = 3;
  public static final int LEFT_RIGHT = 4;

  private static JPanel pnlPreviousOrder;

  private ComponentManager() {}

  public static EmptyBorder getPadding(int position, int padding) {
    EmptyBorder result;
    switch (position) {
      case TOP:
        result = new EmptyBorder(padding, 0, 0, 0);
        break;
      case LEFT:
        result = new EmptyBorder(0, padding, 0, 0);
        break;
      case BOTTOM:
        result = new EmptyBorder(0, 0, padding, 0);
        break;
      case RIGHT:
        result = new EmptyBorder(0, 0, 0, padding);
        break;
      case LEFT_RIGHT:
        result = new EmptyBorder(0, padding, 0, padding);
        break;
      default:
        result = new EmptyBorder(padding, padding, padding, padding);
        break;
    }

    return result;
  }

  public static void toggleActiveOrderPanel(JPanel pnlCurrentOrder) {
    if (pnlPreviousOrder != null) {
      // Reset the previous OrderPanel background color
      pnlPreviousOrder.setBackground(Colors.ORDER_PANEL_BACKGROUND_COLOR);
    }

    // Change the background color of the current OrderPanel
    pnlCurrentOrder.setBackground(Colors.ORDER_PANEL_ACTIVE_BACKGROUND_COLOR);
    pnlPreviousOrder = pnlCurrentOrder;
  }
}
