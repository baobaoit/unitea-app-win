package com.hesmantech.uniteaappwin.manager;

import com.hesmantech.uniteaappwin.gateway.UniteaGateway;

public final class BeanManager {
  private static UniteaGateway uniteaGateway;

  private BeanManager() {}

  public static UniteaGateway getUniteaGateway() {
    return uniteaGateway;
  }

  public static void setUniteaGateway(UniteaGateway uniteaGateway) {
    BeanManager.uniteaGateway = uniteaGateway;
  }
}
