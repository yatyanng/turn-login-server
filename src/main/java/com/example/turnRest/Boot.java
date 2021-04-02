package com.example.turnRest;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Boot {

  private static final Logger log = LoggerFactory.getLogger(Boot.class);

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
      File confDirFile = new File(configDirectory);
      log.info("turn-login-server is started with config directory: {}",
          confDirFile.getAbsolutePath());
    } catch (Exception e) {
      log.error("main", e);
    }
  }
}
