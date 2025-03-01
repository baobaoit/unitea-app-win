package com.hesmantech.uniteaappwin.ui.component;

import javax.swing.JButton;

import com.hesmantech.uniteaappwin.action.PrintButtonClickListener;

public abstract class PrintButton extends JButton {

  private static final long serialVersionUID = 6845832242080773683L;

  {
    this.addActionListener(new PrintButtonClickListener());
  }
}
