package com.vivekraman.inventory.history.analysis.config;

import com.vivekraman.config.SpringdocConfig;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class InventoryHistoryAnalysisSpringdocConfig implements SpringdocConfig.ApiGroup {
  @Override
  public SpringDocConfigProperties.GroupConfig apiGroup() {
    SpringDocConfigProperties.GroupConfig groupConfig = new SpringDocConfigProperties.GroupConfig();
    groupConfig.setDisplayName("inventory-history-analysis");
    groupConfig.setPackagesToScan(Collections.singletonList(
        "com.vivekraman.inventory.history.analysis.controller"));
    return groupConfig;
  }
}
