package com.vivekraman.inventory.history.analysis.service.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryIdentifier;
import com.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import com.vivekraman.inventory.history.analysis.service.api.FileIngestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileIngestServiceImpl implements FileIngestService {
  private final WarehouseInventoryHistoryTransactionRepository txnRepository;

  @Override
  public void ingestHistoryLogFile(AnalysisJob job, MultipartFile file)
      throws Exception {
    InputStream inputStream = file.getInputStream();
    String fileName = file.getOriginalFilename();

    CsvSchema schema = CsvSchema.builder()
        .addColumn("txnPkey")
        .addColumn("class")
        .addColumn("warehouseCode")
        .addColumn("warehouseItemSku")
        .addColumn("warehouseMerchantCode")
        .addColumn("transactionDate")
        .addColumn("transactionDescription")
        .addColumn("transactionQuantity")
        .addColumn("availableStock")
        .addColumn("availableStockTransactionQuantity")
        .addColumn("originalStock")
        .addColumn("originalStockTransactionQuantity")
        .addColumn("orderItemId")
        .addColumn("storeId")
        .addColumn("uniqueId")
        .addColumn("customerLogonId")
        .addColumn("channelId")
        .addColumn("clientId")
        .addColumn("usedApi")
        .addColumn("actionKey")
        .addColumn("actionType")
        .addColumn("createdBy")
        .addColumn("createdDate")
        .addColumn("requestId")
        .addColumn("username")
        .addColumn("version")
        .addColumn("updatedBy")
        .addColumn("updatedDate")
        .setUseHeader(false).build();
    CsvMapper mapper = new CsvMapper();
    MappingIterator<WarehouseInventoryHistoryTransaction> iterator =
        mapper.readerFor(WarehouseInventoryHistoryTransaction.class)
            .with(schema).readValues(inputStream);

    AtomicInteger processedRowCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);
    while (iterator.hasNext()) {
      WarehouseInventoryHistoryTransaction txn = null;
      txn.setAnalysisJob(job);
      txn.setInventoryIdentifier(job.getId());
      try {
        txn = iterator.next();
        if (processedRowCount.getAndIncrement() % 10 == 0) {
          log.debug("Processed {} row(s) of file \"{}\".", processedRowCount.get(), fileName);
        }
      } catch (Exception e) {
        if (processedRowCount.get() == 0) continue;
        log.error("Failed to read row {} of file \"{}\", error ", processedRowCount.get(),
            fileName, e);
        failureCount.incrementAndGet();
        if (failureCount.get() > 10) {
          log.error("Too many ({}) failed row reads. Please check the file \"{}\".",
              failureCount.get(), fileName);
          break;
        }
      }
      if (Objects.nonNull(txn)) {
        this.txnRepository.save(txn);
      }
    }

    log.info("Processed file \"{}\" {} rows with {} errors.", fileName, processedRowCount.get(),
        failureCount.get());
  }
}
