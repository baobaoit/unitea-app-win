package com.hesmantech.uniteaappwin.gateway;

import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.order.ResponseOrderList;

public interface UniteaGateway {
  Order getOrderDetailsById(long orderId);

  ResponseOrderList getOngoingOrders();

  boolean login(String loginId, String password);
}
