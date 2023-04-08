package com.vivekraman.external.config.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.vivekraman.external.config.repository",
                       entityManagerFactoryRef = "externalConfigEntityManagerFactory",
                       transactionManagerRef = "externalConfigTransactionManager")
public class ExternalConfigDatasourceConfig {
  @Bean
  @Primary
  public DataSource externalConfigDataSource(ExternalConfigProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean externalConfigEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("externalConfigDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("com.vivekraman.external.config.entity")
        .persistenceUnit("externalConfig")
        .build();
  }

  @Bean
  @Primary
  public TransactionManager externalConfigTransactionManager(
      @Qualifier("externalConfigEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
