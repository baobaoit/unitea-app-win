package com.hesmantech.uniteaappwin;

import javax.print.PrintService;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.hesmantech.uniteaappwin.gateway.UniteaGateway;
import com.hesmantech.uniteaappwin.manager.BeanManager;
import com.hesmantech.uniteaappwin.manager.PrintDataManager;
import com.hesmantech.uniteaappwin.ui.frame.LoginUI;
import com.hesmantech.uniteaappwin.utils.PrintServiceUtils;

@SpringBootApplication
public class UniteaAppWinApplication implements CommandLineRunner {

  private UniteaGateway uniteaGateway;

  private static final Logger log = LoggerFactory.getLogger(UniteaAppWinApplication.class);

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(UniteaAppWinApplication.class);
    builder.headless(false);
    builder.run(args);
  }

  /** Launch the application. */
  @Override
  public void run(String... args) throws Exception {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      log.error("Error occurred during set look and feel: {}", e.getMessage());
      e.printStackTrace();
    }

    SwingUtilities.invokeLater(
        () -> {
          try {
            BeanManager.setUniteaGateway(uniteaGateway);
            PrintDataManager.put(PrintService.class, PrintServiceUtils.getPrintService());

            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);

          } catch (Exception e) {
            log.error("Error occurred during launch the application: {}", e.getMessage());
            e.printStackTrace();
          }
        });
  }

  @Autowired
  public void setUniteaGateway(UniteaGateway uniteaGateway) {
    this.uniteaGateway = uniteaGateway;
  }
}
