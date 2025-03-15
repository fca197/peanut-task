package com.olivia.peanut.task.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.olivia.sdk.utils.DynamicsPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableHeaderUtils {
  public static <T> void loadHeaderList(DynamicsPage<?> page, String bizKey) {
    try {
      Class<?> headerServiceClass = Class.forName("com.olivia.peanut.base.service.BaseTableHeaderService");
      Object headerService = SpringUtil.getBean(headerServiceClass);
      ReflectUtil.invoke(headerService, "listByBizKey", page, bizKey);
    } catch (Exception e) {
      log.error("loadHeaderList  error :{}", e.getMessage(), e);
    }

  }
}
