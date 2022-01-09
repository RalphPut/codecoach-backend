package com.switchfully.teamair.codecoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodecoachApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodecoachApplication.class, args);
  }

}
