package com.vivekraman.inventory.history.analysis.engine;

import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;

public interface Rule {
  boolean onScan(WarehouseInventoryHistoryTransaction txn);
}
