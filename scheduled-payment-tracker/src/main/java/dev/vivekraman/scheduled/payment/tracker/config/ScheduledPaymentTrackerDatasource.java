package dev.vivekraman.scheduled.payment.tracker.config;

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
@EnableJpaRepositories(basePackages = "dev.vivekraman.scheduled.payment.tracker.repository",
                       entityManagerFactoryRef = "templateEntityManagerFactory",
                       transactionManagerRef = "templateTransactionManager")
public class ScheduledPaymentTrackerDatasource {
  @Bean
  public DataSource templateDataSource(ScheduledPaymentTrackerProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean templateEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("templateDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("dev.vivekraman.module.empty.template.entity")
        .persistenceUnit("template")
        .build();
  }

  @Bean
  public TransactionManager templateTransactionManager(
      @Qualifier("templateEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
