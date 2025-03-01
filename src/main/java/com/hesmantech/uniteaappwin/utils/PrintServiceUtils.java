package com.hesmantech.uniteaappwin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import com.hesmantech.uniteaappwin.Constants;

public class PrintServiceUtils {

  private PrintServiceUtils() {}

  public static String[] getPrintServicesName() {
    return Arrays.stream(getPrintServices()).map(PrintService::getName).toArray(String[]::new);
  }

  public static String getSelectedPrintServiceName() {
    File file = new File(Constants.PRINTER_FILE_TXT);
    String printServiceName = null;
    if (file.exists()) {
      try (BufferedReader bufferedReader =
          new BufferedReader(new FileReader(file.getAbsolutePath()))) {
        printServiceName = bufferedReader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return printServiceName;
  }

  public static void setSelectedPrintServiceName(String name) {
    File file = new File(Constants.PRINTER_FILE_TXT);
    try (BufferedWriter bufferedWriter =
        new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
      bufferedWriter.write(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static PrintService getPrintService() {
    PrintService[] printServices = getPrintServices();
    String selected = getSelectedPrintServiceName();

    if (printServices.length > 0) {
      PrintService ps = printServices[0];
      if (selected != null) {
        for (PrintService p : printServices) {
          if (ps.getName().equals(selected)) {
            return p;
          }
        }
      }
      return ps;
    }
    return null;
  }

  private static PrintService[] getPrintServices() {
    return PrintServiceLookup.lookupPrintServices(null, null);
  }
}
