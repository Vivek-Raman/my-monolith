package dev.vivekraman.inventory.history.analysis.config;

import dev.vivekraman.inventory.history.analysis.constants.ApiPath;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = ApiPath.MODULE_NAME)
@PropertySource("classpath:" + ApiPath.MODULE_NAME + ".properties")
public class InventoryHistoryAnalysisProperties {
  private DataSourceProperties dataSource;
  private int analysisBatchSize;
}
