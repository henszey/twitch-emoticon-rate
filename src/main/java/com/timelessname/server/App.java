package com.timelessname.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableScheduling
@EnableAutoConfiguration
public class App {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }
}
