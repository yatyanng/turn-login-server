package com.example.turn_rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class Boot {

  private static final Logger log = LoggerFactory.getLogger(Boot.class);

  private static ApplicationContext applicationContext;
  
  public static void main(String[] args) {
    try {
      String configDirectory = "conf";
      if (args.length > 0) {
        configDirectory = args[0];
      }
      log.info("config directory: {}", configDirectory);
      System.setProperty("spring.config.location", configDirectory + "/springboot.yml");
      System.setProperty("logging.config", configDirectory + "/logback.xml");
      SpringApplication.run(Boot.class, args);
      log.info("turn-login-server is started with config directory: {}", configDirectory);
    } catch (Exception e) {
      log.error("main", e);
    }
  }
  
  public static RestTemplate sipSoftswitchRestClient() {
    return (RestTemplate) applicationContext.getBean(Constants.SIP_SOFTSWITCH_REST_CLIENT);
  }
}
