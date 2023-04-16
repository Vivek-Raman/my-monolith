package dev.vivekraman.inventory.history.analysis.config;

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
@EnableJpaRepositories(basePackages = "dev.vivekraman.inventory.history.analysis.repository",
                       entityManagerFactoryRef = "inventoryHistoryAnalysisEntityManagerFactory",
                       transactionManagerRef = "inventoryHistoryAnalysisTransactionManager")
public class InventoryHistoryAnalysisDatasourceConfig {
  @Bean
  public DataSource inventoryHistoryAnalysisDataSource(InventoryHistoryAnalysisProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean inventoryHistoryAnalysisEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("inventoryHistoryAnalysisDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("dev.vivekraman.inventory.history.analysis.entity")
        .persistenceUnit("inventoryHistoryAnalysis")
        .build();
  }

  @Bean
  public TransactionManager inventoryHistoryAnalysisTransactionManager(
      @Qualifier("inventoryHistoryAnalysisEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
