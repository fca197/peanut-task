package com.olivia.peanut.task;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoadTaskFlag {
  @PostConstruct
  public void init() {
    log.info("peanut  task init");
  }
}
