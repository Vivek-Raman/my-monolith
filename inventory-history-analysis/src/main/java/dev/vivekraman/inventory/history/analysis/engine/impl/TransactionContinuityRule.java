package dev.vivekraman.inventory.history.analysis.engine.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vivekraman.inventory.history.analysis.engine.Rule;
import dev.vivekraman.inventory.history.analysis.engine.RuleMetadata;
import dev.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import dev.vivekraman.inventory.history.analysis.entity.RuleState;
import dev.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import dev.vivekraman.inventory.history.analysis.repository.RuleStateRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionContinuityRule implements Rule {
  private static final String PROCESS_KEY = "process";
  private static final String ERROR_KEY = "error";

  private final ObjectMapper mapper;
  private final RuleStateRepository ruleStateRepository;

  @Override
  public String ruleID() {
    return this.getClass().getSimpleName();
  }

  @Override
  public boolean onScan(AnalysisJob job, WarehouseInventoryHistoryTransaction txn)
      throws Exception {
    RuleState state = ruleStateRepository.findByRuleIdAndInventoryIdentifierAndStateKey(
        ruleID(), txn.getInventoryIdentifier(), PROCESS_KEY);
    if (Objects.isNull(state)) {
      state = RuleState.builder()
          .ruleId(ruleID())
          .inventoryIdentifier(txn.getInventoryIdentifier())
          .analysisJob(job)
          .ignoreEndState(Boolean.TRUE)
          .stateKey(PROCESS_KEY)
          .build();
    }

    TransactionContinuityRuleMetadata data = TransactionContinuityRuleMetadata.readFromRuleState(
        mapper, state);
    if (Objects.nonNull(data)) {
      if (data.getAvailableStock() + txn.getAvailableStockTransactionQuantity() != txn.getAvailableStock()) {
        invalidateAvailableStock(job, state, data, txn);
      }
      if (data.getOriginalStock() + txn.getOriginalStockTransactionQuantity() != txn.getOriginalStock()) {
        invalidateOriginalStock(job, state, data, txn);
      }
    }

    data = TransactionContinuityRuleMetadata.builder()
        .availableStock(txn.getAvailableStock())
        .originalStock(txn.getOriginalStock())
        .lastPrimaryKey(txn.getTransactionPrimaryKey())
        .build();
    data.writeToRuleState(mapper, state);

    ruleStateRepository.save(state);
    return true;
  }

  private void invalidateOriginalStock(AnalysisJob job, RuleState state,
      TransactionContinuityRuleMetadata data, WarehouseInventoryHistoryTransaction txn)
      throws Exception {
    RuleState invalidState = RuleState.builder()
        .ruleId(state.getRuleId())
        .stateKey(state.getStateKey())
        .inventoryIdentifier(state.getInventoryIdentifier())
        .analysisJob(job)
        .stateKey(ERROR_KEY)
        .build();
    data.writeToRuleState(mapper, invalidState);
    ruleStateRepository.save(invalidState);

    Rule.super.invalidate(log, txn,
        String.format("Original  stock mismatch with txn %s! Expected: %d\t+ %d\t\t| Actual: %d",
        data.getLastPrimaryKey(), data.getOriginalStock(), txn.getOriginalStockTransactionQuantity(),
        txn.getOriginalStock()));
  }

  private void invalidateAvailableStock(AnalysisJob job, RuleState state,
      TransactionContinuityRuleMetadata data,
      WarehouseInventoryHistoryTransaction txn) throws Exception {
    RuleState invalidState = RuleState.builder()
        .ruleId(state.getRuleId())
        .stateKey(state.getStateKey())
        .inventoryIdentifier(state.getInventoryIdentifier())
        .analysisJob(job)
        .stateKey(ERROR_KEY)
        .build();
    data.writeToRuleState(mapper, invalidState);
    ruleStateRepository.save(invalidState);

    Rule.super.invalidate(log, txn,
        String.format("Available stock mismatch with txn %s! Expected: %d\t+ %d\t\t| Actual: %d",
            data.getLastPrimaryKey(), data.getAvailableStock(),
            txn.getAvailableStockTransactionQuantity(), txn.getAvailableStock()));
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  static class TransactionContinuityRuleMetadata extends RuleMetadata {
    @Serial
    private static final long serialVersionUID = 7401456732285601266L;

    private int availableStock;
    private int originalStock;
    private String lastPrimaryKey;

    public static TransactionContinuityRuleMetadata readFromRuleState(
        ObjectMapper mapper, RuleState state) throws Exception {
      return RuleMetadata.readFromRuleState(mapper, state, TransactionContinuityRuleMetadata.class);
    }
  }
}
