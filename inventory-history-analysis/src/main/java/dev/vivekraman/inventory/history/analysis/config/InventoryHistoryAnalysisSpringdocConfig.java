package dev.vivekraman.inventory.history.analysis.config;

import dev.vivekraman.inventory.history.analysis.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryHistoryAnalysisSpringdocConfig {
  @Bean
  public GroupedOpenApi inventoryHistoryAnalysisApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("com.vivekraman.inventory.history.analysis.controller")
        .build();
  }
}
