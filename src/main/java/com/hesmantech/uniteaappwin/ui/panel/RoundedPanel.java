package com.hesmantech.uniteaappwin.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.util.Optional;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

  private static final long serialVersionUID = 5847533878820573529L;

  private Color borderColor;
  private boolean enabledBorder;
  private int cornerRadius = 15;

  public RoundedPanel(LayoutManager layout, int cornerRadius) {
    this.cornerRadius = cornerRadius;
    setLayout(layout);
  }

  public void setCornerRadius(int cornerRadius) {
    this.cornerRadius = cornerRadius;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponents(g);
    Dimension arcs = new Dimension(cornerRadius, cornerRadius);
    int width = getWidth();
    int height = getHeight();
    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int dx = 0;
    int dy = 0;
    int dWidth = 1;
    int dHeight = 1;
    if (enabledBorder) {
      graphics.setColor(Optional.ofNullable(borderColor).orElseGet(this::getBackground));
      graphics.fillRoundRect(dx, dy, width - dWidth, height - dHeight, arcs.width, arcs.height);
      graphics.drawRoundRect(dx, dy, width - dWidth, height - dHeight, arcs.width, arcs.height);
      dx = 2;
      dy = 2;
      dWidth = 5;
      dHeight = 5;
    }
    graphics.setColor(getBackground());
    graphics.fillRoundRect(dx, dy, width - dWidth, height - dHeight, arcs.width, arcs.height);
    graphics.drawRoundRect(dx, dy, width - dWidth, height - dHeight, arcs.width, arcs.height);
  }

  public void setEnabledBorder(boolean enabledBorder) {
    this.enabledBorder = enabledBorder;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }
}
