package dev.vivekraman.scheduled.payment.tracker.config;

import dev.vivekraman.scheduled.payment.tracker.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledPaymentTrackerSpringdocConfig {
  @Bean
  public GroupedOpenApi templateApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("dev.vivekraman.scheduled.payment.tracker.controller")
        .build();
  }
}
