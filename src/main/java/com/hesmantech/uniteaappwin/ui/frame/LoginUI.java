package com.hesmantech.uniteaappwin.ui.frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.hesmantech.uniteaappwin.constant.FontConstants;
import com.hesmantech.uniteaappwin.gateway.UniteaGateway;
import com.hesmantech.uniteaappwin.manager.BeanManager;
import com.hesmantech.uniteaappwin.manager.EventManager;

public class LoginUI extends JFrame implements ActionListener {

  private static final long serialVersionUID = 7251617216949749073L;
  private static final char DEFAULT_PASSWORD_FIELD_ECHO_CHAR =
      (char) UIManager.get("PasswordField.echoChar");

  private UniteaGateway uniteaGateway;

  private JButton btnLogin;
  private JButton btnReset;
  private JCheckBox chkShowPassword;
  private JLabel lblUserName;
  private JLabel lblPassword;
  private JPanel pnlMain;
  private JPasswordField txtPassword;
  private JTextField txtUserName;

  public LoginUI() {
    setSize(new Dimension(550, 250));
    setPreferredSize(new Dimension(550, 250));
    setResizable(false);
    setLocationRelativeTo(null);
    pnlMain = new JPanel();
    pnlMain.setLayout(null);

    this.uniteaGateway = BeanManager.getUniteaGateway();
    initComponentDisplay();
    addEventAction();
    setTitle("Login - Unitea");
    setContentPane(pnlMain);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
  }

  private void initComponentDisplay() {
    lblUserName = new JLabel("User name");
    lblPassword = new JLabel("Password");
    txtUserName = new JTextField();
    txtPassword = new JPasswordField();
    btnLogin = new JButton("Login");
    btnReset = new JButton("Reset");
    chkShowPassword = new JCheckBox("Show password");

    lblUserName.setBounds(150, 50, 100, 30);
    lblPassword.setBounds(150, 85, 100, 30);
    txtUserName.setBounds(250, 50, 150, 30);
    txtPassword.setBounds(250, 85, 150, 30);
    chkShowPassword.setBounds(250, 125, 152, 30);
    btnLogin.setBounds(150, 165, 100, 30);
    btnReset.setBounds(250, 165, 100, 30);

    lblUserName.setLabelFor(txtUserName);
    lblPassword.setLabelFor(txtPassword);

    lblUserName.setFont(FontConstants.DEFAULT_FONT_16);
    lblPassword.setFont(FontConstants.DEFAULT_FONT_16);
    txtUserName.setFont(FontConstants.DEFAULT_FONT_16);
    txtPassword.setFont(FontConstants.DEFAULT_FONT_16);
    chkShowPassword.setFont(FontConstants.DEFAULT_FONT_16);
    btnLogin.setFont(FontConstants.DEFAULT_FONT_16);
    btnReset.setFont(FontConstants.DEFAULT_FONT_16);

    pnlMain.add(lblUserName);
    pnlMain.add(lblPassword);
    pnlMain.add(txtUserName);
    pnlMain.add(txtPassword);
    pnlMain.add(chkShowPassword);
    pnlMain.add(btnLogin);
    pnlMain.add(btnReset);
  }

  private void addEventAction() {
    btnReset.addActionListener(this);
    chkShowPassword.addActionListener(this);

    Action action =
        new AbstractAction() {
          private static final long serialVersionUID = -5385872022899889950L;

          @Override
          public void actionPerformed(ActionEvent e) {
            loginEvent();
          }
        };

    btnLogin
        .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke("ENTER"), "login");
    btnLogin.getActionMap().put("login", action);
    btnLogin.addActionListener(action);
  }

  private void loginEvent() {
    if (uniteaGateway.login(txtUserName.getText(), String.valueOf(txtPassword.getPassword()))) {
      this.dispose();
      UniteaAppWinUI ui = new UniteaAppWinUI();
      EventManager.setFrmMain(ui);
      EventManager.showFirstOrderDetails();
      ui.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(null, "Wrong username or password!\nPlease retry");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnLogin) {
      loginEvent();
    }

    if (e.getSource() == btnReset) {
      txtUserName.setText("");
      txtPassword.setText("");
    }

    if (e.getSource() == chkShowPassword) {
      if (chkShowPassword.isSelected()) {
        txtPassword.setEchoChar((char) 0);
      } else {
        txtPassword.setEchoChar(DEFAULT_PASSWORD_FIELD_ECHO_CHAR);
      }
    }
  }
}
