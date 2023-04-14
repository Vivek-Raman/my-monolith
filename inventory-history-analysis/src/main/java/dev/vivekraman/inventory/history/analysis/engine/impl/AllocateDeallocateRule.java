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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AllocateDeallocateRule implements Rule {
  private static final String ACTION_KEY_ALLOCATE = "UPDATE_STOCK_FOR_ALLOCATE";
  private static final String ACTION_KEY_DEALLOCATE = "UPDATE_STOCK_FOR_DEALLOCATE";

  private final RuleStateRepository ruleStateRepository;
  private final ObjectMapper objectMapper;

  @Override
  public String ruleID() {
    return this.getClass().getSimpleName();
  }

  @Override
  public boolean onScan(AnalysisJob job, WarehouseInventoryHistoryTransaction txn)
      throws Exception {

    // locate existing state
    RuleState state = ruleStateRepository.findByRuleIdAndInventoryIdentifierAndStateKey(ruleID(),
        txn.getInventoryIdentifier(), txn.getUniqueId());

    // if exists, add txn reserved stock to metadata reserved stock
    AllocateDeallocateRuleMetadata data;
    if (Objects.nonNull(state)) {
      data = AllocateDeallocateRuleMetadata.readFromRuleState(objectMapper, state);
      // FIXME: there is a bug where these numbers aren't purely accumulative,
      //  so this code will break
      data.setReservedStock(data.getReservedStock() + txn.getAvailableStockTransactionQuantity());
    }
    // else, create metadata and store reserved stock
    else {
      state = RuleState.builder()
          .ruleId(ruleID())
          .inventoryIdentifier(txn.getInventoryIdentifier())
          .analysisJob(job)
          .stateKey(txn.getUniqueId()).build();
      data = AllocateDeallocateRuleMetadata.builder()
          .reservedStock(txn.getAvailableStockTransactionQuantity()).build();
    }
    data.writeToRuleState(objectMapper, state);

    // if reservedStock is 0, clear state for this unique ID
    if (data.getReservedStock() == 0) {
      ruleStateRepository.delete(state);
      return true;
    }

    ruleStateRepository.save(state);
    return true;
  }

  @Override
  public boolean validateForRule(WarehouseInventoryHistoryTransaction txn) {
    if (StringUtils.isBlank(txn.getUniqueId())) {
      return false;
    }

    Set<String> keys = new HashSet<>(2);
    keys.add(ACTION_KEY_ALLOCATE);
    keys.add(ACTION_KEY_DEALLOCATE);
    if (!keys.contains(txn.getActionKey())) {
      return false;
    }

    if (!"null".equals(txn.getOrderItemId())) {
      return false;
    }

    return true;
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  static class AllocateDeallocateRuleMetadata extends RuleMetadata {
    @Serial
    private static final long serialVersionUID = 9040766486117975307L;

    private int reservedStock;

    public static AllocateDeallocateRuleMetadata readFromRuleState(ObjectMapper mapper,
        RuleState state) throws Exception {
      return RuleMetadata.readFromRuleState(mapper, state, AllocateDeallocateRuleMetadata.class);
    }
  }
}
