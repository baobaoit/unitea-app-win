package com.hesmantech.uniteaappwin.ui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.print.PrintService;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hesmantech.uniteaappwin.Constants;
import com.hesmantech.uniteaappwin.constant.FontConstants;
import com.hesmantech.uniteaappwin.manager.PrintDataManager;
import com.hesmantech.uniteaappwin.ui.component.TestPrinterButton;
import com.hesmantech.uniteaappwin.ui.frame.LoginUI;
import com.hesmantech.uniteaappwin.ui.frame.UniteaAppWinUI;
import com.hesmantech.uniteaappwin.utils.PrintServiceUtils;

public class SettingPanel extends JPanel {

  private static final long serialVersionUID = 2190204940072034846L;

  public SettingPanel() {
    setLayout(new GridBagLayout());
    JComboBox<String> cmbPrinter = initCmbPrinterData();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weighty = 1.0;
    gbc.weightx = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    add(cmbPrinter, gbc);

    JButton btnTestPrinter = new TestPrinterButton("print");
    btnTestPrinter.setFont(FontConstants.DEFAULT_FONT_16);
    gbc.gridx = 1;
    add(btnTestPrinter, gbc);

    gbc.anchor = GridBagConstraints.SOUTHEAST;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 1;

    add(initBtnLogout(), gbc);
  }

  private JComboBox<String> initCmbPrinterData() {
    JComboBox<String> cmbPrinter = new JComboBox<>();

    String[] printServices = PrintServiceUtils.getPrintServicesName();
    String previousSelectedPrintServiceName = PrintServiceUtils.getSelectedPrintServiceName();
    if (printServices.length == 0) {
      cmbPrinter.addItem(Constants.NO_PRINTER_FOUND);
    } else {
      cmbPrinter = new JComboBox<>(printServices);

      if (previousSelectedPrintServiceName != null) {
        cmbPrinter.setSelectedItem(previousSelectedPrintServiceName);
      } else {
        cmbPrinter.setSelectedIndex(0);
        PrintServiceUtils.setSelectedPrintServiceName((String) cmbPrinter.getSelectedItem());
      }
    }

    cmbPrinter.addActionListener(
        e -> {
          String selectedItem = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();
          assert selectedItem != null;
          if (!selectedItem.equalsIgnoreCase(Constants.NO_PRINTER_FOUND)) {
            PrintServiceUtils.setSelectedPrintServiceName(selectedItem);
            PrintDataManager.put(PrintService.class, PrintServiceUtils.getPrintService());
          }
        });

    cmbPrinter.setFont(FontConstants.DEFAULT_FONT_16);
    return cmbPrinter;
  }

  private JButton initBtnLogout() {
    JButton btnLogout = new JButton("Logout");

    btnLogout.setFont(FontConstants.DEFAULT_FONT_16);
    btnLogout.addActionListener(
        e -> {
          UniteaAppWinUI frame = (UniteaAppWinUI) SwingUtilities.getRoot(btnLogout);
          frame.dispose();
          LoginUI loginUI = new LoginUI();
          loginUI.setVisible(true);
        });

    return btnLogout;
  }
}
