package com.vivekraman.inventory.history.analysis.service.api;

import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;

public interface AnalysisService {
  void analyzeAsync(AnalysisJob job, String inventoryIdentifier);

  void analyze(AnalysisJob job, String inventoryIdentifier);
}
