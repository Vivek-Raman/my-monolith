package com.vivekraman.inventory.history.analysis.engine.impl;

import com.vivekraman.inventory.history.analysis.engine.Rule;
import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import com.vivekraman.inventory.history.analysis.repository.RuleStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllocateDeallocateRule implements Rule {
  private static final String ACTION_KEY_ALLOCATE = "UPDATE_STOCK_FOR_ALLOCATE";
  private static final String ACTION_KEY_DEALLOCATE = "UPDATE_STOCK_FOR_DEALLOCATE";

  @Autowired
  private RuleStateRepository ruleStateRepository;

  @Override
  public boolean onScan(WarehouseInventoryHistoryTransaction txn) {
//    if ()
    return true;
  }
}
