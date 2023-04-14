package dev.vivekraman.external.config.service.impl;

import dev.vivekraman.external.config.entity.ExternalConfigParameter;
import dev.vivekraman.external.config.repository.ExternalConfigParameterRepository;
import dev.vivekraman.external.config.service.api.ExternalConfigParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ExternalConfigParameterServiceImpl implements ExternalConfigParameterService {
  private final ExternalConfigParameterRepository externalConfigParameterRepository;

  @Override
  public Page<ExternalConfigParameter> findAll() {
    return this.externalConfigParameterRepository.findAll(PageRequest.of(0, 100));
  }

  @Override
  public ExternalConfigParameter findByConfigKey(String configKey) {
    return this.externalConfigParameterRepository.findByConfigKey(configKey).orElseThrow();
  }

  @Override
  public Page<ExternalConfigParameter> findByConfigKeyIn(String... configKeys) {
    return this.externalConfigParameterRepository.findByConfigKeyIn(Arrays.asList(configKeys),
        PageRequest.of(0, configKeys.length));
  }

  @Override
  public ExternalConfigParameter upsert(String configKey, String configValue) {
    ExternalConfigParameter param = this.externalConfigParameterRepository.findByConfigKey(configKey)
        .orElseGet(() -> ExternalConfigParameter.builder().configKey(configKey).build());
    param.setConfigValue(configValue);
    return this.externalConfigParameterRepository.save(param);
  }
}
