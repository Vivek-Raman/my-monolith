package com.vivekraman.inventory.history.analysis.service.api;

public interface AnalysisService {
  void analyzeAsync(String inventoryIdentifier);

  void analyze(String inventoryIdentifier);
}
