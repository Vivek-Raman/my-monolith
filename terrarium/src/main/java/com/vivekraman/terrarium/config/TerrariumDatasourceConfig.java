package com.vivekraman.terrarium.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.vivekraman.terrarium.repository",
                       entityManagerFactoryRef = "terrariumEntityManagerFactory",
                       transactionManagerRef = "terrariumTransactionManager")
public class TerrariumDatasourceConfig {
  @Bean
  public DataSource terrariumDataSource(TerrariumProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean terrariumEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("terrariumDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("com.vivekraman.terrarium.entity")
        .persistenceUnit("terrarium")
        .build();
  }

  @Bean
  public TransactionManager terrariumTransactionManager(
      @Qualifier("terrariumEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
