package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.repository.AnalysisJobRepository;
import com.vivekraman.inventory.history.analysis.repository.RuleStateRepository;
import com.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import com.vivekraman.inventory.history.analysis.service.api.DBCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DBCleanupServiceImpl implements DBCleanupService {
  private final List<CrudRepository<?, String>> repositories;

  @Autowired
  public DBCleanupServiceImpl(AnalysisJobRepository analysisJobRepository,
      RuleStateRepository ruleStateRepository,
      WarehouseInventoryHistoryTransactionRepository warehouseInventoryHistoryTransactionRepository) {
    this.repositories = new ArrayList<>();
    this.repositories.add(analysisJobRepository);
    this.repositories.add(ruleStateRepository);
    this.repositories.add(warehouseInventoryHistoryTransactionRepository);
  }

  @Override
  public void cleanupDB() {
    this.repositories.forEach(CrudRepository::deleteAll);
  }
}
