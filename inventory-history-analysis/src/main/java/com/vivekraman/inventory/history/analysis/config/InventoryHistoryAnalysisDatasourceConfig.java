package com.vivekraman.inventory.history.analysis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(basePackages = "com.vivekraman.inventory.history.analysis.repository",
                        jdbcOperationsRef = "inventoryHistoryAnalysisJdbcTemplate")
public class InventoryHistoryAnalysisDatasourceConfig extends AbstractJdbcConfiguration {
  @Bean
  public DataSource inventoryHistoryAnalysisDataSource(InventoryHistoryAnalysisProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public NamedParameterJdbcOperations inventoryHistoryAnalysisJdbcTemplate(
      @Qualifier("inventoryHistoryAnalysisDataSource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
}
