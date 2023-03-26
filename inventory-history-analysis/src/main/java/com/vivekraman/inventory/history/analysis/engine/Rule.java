package com.vivekraman.inventory.history.analysis.engine;

import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;

public interface Rule {
  String ruleID();
  boolean onScan(AnalysisJob job, WarehouseInventoryHistoryTransaction txn) throws Exception;

  default boolean isActive() {
    return true;
  }

  boolean validateForRule(WarehouseInventoryHistoryTransaction txn);
}
