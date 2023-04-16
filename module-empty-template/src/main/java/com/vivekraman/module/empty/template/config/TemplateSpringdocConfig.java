package dev.vivekraman.module.empty.template.config;

import dev.vivekraman.module.empty.template.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateSpringdocConfig {
  @Bean
  public GroupedOpenApi templateApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("dev.vivekraman.module.empty.template.controller")
        .build();
  }
}
