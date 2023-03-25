package com.vivekraman.inventory.history.analysis.config;

import com.vivekraman.inventory.history.analysis.constants.ApiPath;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = ApiPath.MODULE_NAME)
public class InventoryHistoryAnalysisProperties {
  private int analysisBatchSize;
}
