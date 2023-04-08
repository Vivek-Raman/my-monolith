package com.vivekraman.terrarium.config;

import com.vivekraman.terrarium.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TerrariumSpringdocConfig {
  @Bean
  public GroupedOpenApi terrariumApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("com.vivekraman.terrarium.controller")
        .build();
  }
}
