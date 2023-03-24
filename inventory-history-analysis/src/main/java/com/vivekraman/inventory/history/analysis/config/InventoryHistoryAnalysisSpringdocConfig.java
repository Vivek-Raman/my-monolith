package com.vivekraman.inventory.history.analysis.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryHistoryAnalysisSpringdocConfig {
  private static final String API_GROUP = "inventory-history-analysis";

  @Bean
  public GroupedOpenApi inventoryHistoryAnalysisApiGroup() {
    return GroupedOpenApi.builder()
        .group(API_GROUP)
        .packagesToScan("com.vivekraman.inventory.history.analysis.controller")
        .build();
  }
}
