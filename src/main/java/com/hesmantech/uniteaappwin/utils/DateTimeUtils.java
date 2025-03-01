package com.hesmantech.uniteaappwin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hesmantech.uniteaappwin.constant.DateTimeFormatConstants;

public final class DateTimeUtils {
  private static final Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

  private DateTimeUtils() {}

  public static String formatInvoiceDatePurchased(String invoiceDatePurchased, String pattern) {
    String result = "N/A";
    try {
      result =
          new SimpleDateFormat(pattern)
              .format(
                  new SimpleDateFormat(DateTimeFormatConstants.UNITEA_INVOICE_DATE_PURCHASED_FORMAT)
                      .parse(invoiceDatePurchased));
    } catch (ParseException e) {
      log.error("Error occurred during parse invoice date purchased: {}", e.getMessage());
      e.printStackTrace();
    }

    return result;
  }
}
