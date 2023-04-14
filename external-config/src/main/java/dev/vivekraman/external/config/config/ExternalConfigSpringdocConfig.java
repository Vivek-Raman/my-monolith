package dev.vivekraman.external.config.config;

import dev.vivekraman.external.config.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalConfigSpringdocConfig {
  @Bean
  public GroupedOpenApi externalConfigApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("com.vivekraman.external.config.controller")
        .build();
  }
}
