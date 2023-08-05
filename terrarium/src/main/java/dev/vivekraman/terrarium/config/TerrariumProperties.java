package dev.vivekraman.terrarium.config;

import dev.vivekraman.terrarium.constants.TerriariumApiPath;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = TerriariumApiPath.MODULE_NAME)
@PropertySource("classpath:" + TerriariumApiPath.MODULE_NAME + ".properties")
public class TerrariumProperties {
  private DataSourceProperties dataSource;
}
