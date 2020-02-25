package com.task.slackapi;

import com.task.slackapi.config.PropertiesInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.task.slackapi",lazyInit = true)
public class Application {
  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
        .initializers(new PropertiesInitializer())
        .run(args);
  }
}
