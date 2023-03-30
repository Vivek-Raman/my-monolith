package com.vivekraman.inventory.history.analysis.repository;

import com.vivekraman.inventory.history.analysis.entity.RuleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleStateRepository extends CrudRepository<RuleState, String> {
  RuleState findByRuleIdAndInventoryIdentifierAndStateKey(String ruleID,
      String inventoryIdentifier, String stateKey);

  Page<RuleState> findAllByRuleIdAndIgnoreEndStateFalse(String ruleID, Pageable pageable);
}
