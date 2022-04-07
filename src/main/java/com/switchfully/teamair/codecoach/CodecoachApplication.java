package com.switchfully.teamair.codecoach;

import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodecoachApplication {

  public static final String ACCOUNT_SID = "AC6e49a4ec4a2641584f6380a3cef456a7";
  public static final String AUTH_TOKEN = "0f2b20f56e7d935c09b57e74c4a17e59";


  public static void main(String[] args) {
    SpringApplication.run(CodecoachApplication.class, args);
   // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }



}
