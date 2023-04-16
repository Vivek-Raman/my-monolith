package dev.vivekraman.fix.subscription.dates.config;

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
@EnableJpaRepositories(basePackages = "dev.vivekraman.fix.subscription.dates.repository",
                       entityManagerFactoryRef = "fixSubscriptionDatesEntityManagerFactory",
                       transactionManagerRef = "fixSubscriptionDatesTransactionManager")
public class FixSubscriptionDatesDatasource {
  @Bean
  public DataSource fixSubscriptionDatesDataSource(FixSubscriptionDatesProperties moduleProps) {
    return moduleProps.getDataSource().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean fixSubscriptionDatesEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("fixSubscriptionDatesDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("dev.vivekraman.fix.subscription.dates.entity")
        .persistenceUnit("template")
        .build();
  }

  @Bean
  public TransactionManager fixSubscriptionDatesTransactionManager(
      @Qualifier("fixSubscriptionDatesEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
