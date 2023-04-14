package com.vivekraman.fix.subscription.dates.config;

import com.vivekraman.fix.subscription.dates.constants.ApiPath;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FixSubscriptionDatesSpringdocConfig {
  @Bean
  public GroupedOpenApi fixSubscriptionDatesApiGroup() {
    return GroupedOpenApi.builder()
        .group(ApiPath.MODULE_NAME)
        .packagesToScan("com.vivekraman.fix.subscription.dates.controller")
        .build();
  }
}