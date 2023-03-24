package com.vivekraman.inventory.history.analysis.config;

import com.vivekraman.inventory.history.analysis.constants.EntityConstants;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(basePackages = "com.vivekraman.inventory.history.analysis.repository",
                        jdbcOperationsRef = "h2JdbcTemplate")
public class H2DatasourceConfig extends AbstractJdbcConfiguration {
  @Bean
  public DataSource h2DataSource() {
    DataSourceProperties props = new DataSourceProperties();
    props.setType(JdbcDataSource.class);
    props.setUrl("jdbc:h2:mem:" + EntityConstants.DB_NAME + ";DB_CLOSE_DELAY=-1");
    props.setUsername("h2test");
    props.setPassword("h2test");
    props.setDriverClassName("org.h2.Driver");
    return props.initializeDataSourceBuilder().build();
  }

  @Bean
  public NamedParameterJdbcOperations h2JdbcTemplate(@Qualifier("h2DataSource") DataSource h2DataSource) {
    return new NamedParameterJdbcTemplate(h2DataSource);
  }
}
