package com.hesmantech.uniteaappwin.manager;

import java.util.HashMap;
import java.util.Map;

public final class PrintDataManager {
  private static Map<Class<?>, Object> datas;

  private PrintDataManager() {}

  public static void put(Class<?> clazz, Object obj) {
    if (datas == null) {
      datas = new HashMap<>();
    }

    datas.put(clazz, obj);
  }

  @SuppressWarnings("unchecked")
  public static <T> T get(Class<T> clazz) {
    T data = (T) datas.get(clazz);
    if (data == null) {
      try {
        data = clazz.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }

      datas.put(clazz, data);
    }
    return data;
  }
}
