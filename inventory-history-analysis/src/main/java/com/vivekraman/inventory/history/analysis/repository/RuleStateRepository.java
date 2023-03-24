package com.vivekraman.inventory.history.analysis.repository;

import com.vivekraman.inventory.history.analysis.entity.RuleState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleStateRepository extends CrudRepository<RuleState, String> {}
