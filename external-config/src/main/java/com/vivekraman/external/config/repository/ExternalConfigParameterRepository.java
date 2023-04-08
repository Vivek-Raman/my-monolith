package com.vivekraman.external.config.repository;


import com.vivekraman.external.config.entity.ExternalConfigParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalConfigParameterRepository
    extends CrudRepository<ExternalConfigParameter, String> {
  Page<ExternalConfigParameter> findAll(Pageable pageable);
  Optional<ExternalConfigParameter> findByConfigKey(String configKey);
  Page<ExternalConfigParameter> findByConfigKeyIn(List<String> configKey, Pageable pageable);
}
