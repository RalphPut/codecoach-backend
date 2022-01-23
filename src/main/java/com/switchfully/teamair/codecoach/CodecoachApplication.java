package com.switchfully.teamair.codecoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodecoachApplication {
  public static final String ACCOUNT_SID = "AC6e49a4ec4a2641584f6380a3cef456a7";
  public static final String AUTH_TOKEN = "27b5fd45388765a064f2f143a6370fda";

  public static void main(String[] args) {
    SpringApplication.run(CodecoachApplication.class, args);
  }

}
