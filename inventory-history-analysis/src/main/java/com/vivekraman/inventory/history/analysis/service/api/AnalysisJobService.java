package com.vivekraman.inventory.history.analysis.service.api;

import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryIdentifier;

public interface AnalysisJobService {
  AnalysisJob initiateIngest(WarehouseInventoryIdentifier inventory, String fileName);
  AnalysisJob initiateAnalysis(WarehouseInventoryIdentifier inventory);
  AnalysisJob findByIdentifier(WarehouseInventoryIdentifier inventory);
}
