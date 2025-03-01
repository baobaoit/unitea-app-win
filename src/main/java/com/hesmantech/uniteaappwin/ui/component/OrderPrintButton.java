package com.hesmantech.uniteaappwin.ui.component;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import java.awt.Color;

public class OrderPrintButton extends PrintButton {

  private static final long serialVersionUID = 3430919988266866076L;

  public OrderPrintButton(Icon imageIcon) {
    setBorder(BorderFactory.createEmptyBorder());
    setBackground(Color.WHITE);
    setOpaque(true);
    setIcon(imageIcon);
  }
}
