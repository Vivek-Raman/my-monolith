package dev.vivekraman.inventory.history.analysis.engine;

import dev.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import dev.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import org.slf4j.Logger;

public interface Rule {
  String ruleID();
  boolean onScan(AnalysisJob job, WarehouseInventoryHistoryTransaction txn) throws Exception;

  default boolean isActive() {
    return true;
  }

  default boolean validateForRule(WarehouseInventoryHistoryTransaction txn) {
    return true;
  }

  default void invalidate(Logger log, WarehouseInventoryHistoryTransaction txn) {
    log.error("Invalid record as per {}, txnID {}", ruleID(), txn.getTransactionPrimaryKey());
  }

  default void invalidate(Logger log, WarehouseInventoryHistoryTransaction txn,
      String message) {
    log.error("Invalid record as per {}, txnID {}: {}", ruleID(),
        txn.getTransactionPrimaryKey(), message);
  }
}
