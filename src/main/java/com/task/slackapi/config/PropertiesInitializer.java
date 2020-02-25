package com.task.slackapi.config;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

@Configuration
@Slf4j
public class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final List<String> externalPropFiles = ImmutableList.of("config.properties",
                                                                         "token.properties");

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    final ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
    String propertyFilesPath = environment.getProperty("props.file.path");
    final MutablePropertySources mutablePropertySources = environment.getPropertySources();
    for (String propFile : externalPropFiles) {
      try {
        mutablePropertySources
            .addLast(new ResourcePropertySource("file:" + propertyFilesPath + "/" + propFile));
      } catch (Exception e) {
        log.error("Error reading property files from {}", propertyFilesPath, e);
      }
    }
  }
}
