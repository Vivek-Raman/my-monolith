package dev.vivekraman.external.config.service.api;

import dev.vivekraman.external.config.entity.ExternalConfigParameter;
import org.springframework.data.domain.Page;


public interface ExternalConfigParameterService {
  Page<ExternalConfigParameter> findAll();

  ExternalConfigParameter findByConfigKey(String configKey);
  Page<ExternalConfigParameter> findByConfigKeyIn(String... configKey);

  ExternalConfigParameter upsert(String configKey, String configValue);
}
