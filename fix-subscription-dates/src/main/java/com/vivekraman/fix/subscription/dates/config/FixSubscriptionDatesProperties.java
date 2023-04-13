package com.vivekraman.fix.subscription.dates.config;

import com.vivekraman.fix.subscription.dates.constants.ApiPath;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = ApiPath.MODULE_NAME)
@PropertySource("classpath:" + ApiPath.MODULE_NAME + ".properties")
public class FixSubscriptionDatesProperties {
  private DataSourceProperties dataSource;
}
