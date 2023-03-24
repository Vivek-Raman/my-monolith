package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
  private final WarehouseInventoryHistoryTransactionRepository txnRepository;

  @Override
  public void analyze(String inventoryIdentifier) {
    // TODO: fetch from repo in batches, ordered by date
    // TODO: apply each rule sequentially
  }
}
