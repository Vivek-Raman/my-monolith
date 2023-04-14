package dev.vivekraman.inventory.history.analysis.repository;

import dev.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseInventoryHistoryTransactionRepository
    extends CrudRepository<WarehouseInventoryHistoryTransaction, String> {
  Page<WarehouseInventoryHistoryTransaction> findByUniqueIdStartsWith(String uniqueIdSearchQuery,
      Pageable pageable);

  Page<WarehouseInventoryHistoryTransaction> findByInventoryIdentifierOrderByTransactionDate(
     String inventoryIdentifier, Pageable pageable);
}
