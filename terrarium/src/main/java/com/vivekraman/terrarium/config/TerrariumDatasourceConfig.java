package com.vivekraman.terrarium.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(basePackages = "com.vivekraman.terrarium.repository",
                        jdbcOperationsRef = "terrariumJdbcTemplate")
public class TerrariumDatasourceConfig extends AbstractJdbcConfiguration {
  @Bean
  public DataSource terrariumDataSource(TerrariumProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public NamedParameterJdbcOperations terrariumJdbcTemplate(
      @Qualifier("terrariumDataSource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
}
