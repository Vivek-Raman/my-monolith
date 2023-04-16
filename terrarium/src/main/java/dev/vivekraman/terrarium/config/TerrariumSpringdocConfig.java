package dev.vivekraman.terrarium.config;

import dev.vivekraman.terrarium.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TerrariumSpringdocConfig {
  @Bean
  public GroupedOpenApi terrariumApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("dev.vivekraman.terrarium.controller")
        .build();
  }
}
