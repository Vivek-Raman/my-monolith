package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.service.api.ExcelFileHelper;
import com.vivekraman.inventory.history.analysis.service.api.ExcelFileProcessor;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryHistoryTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ExcelFileProcessorImpl implements ExcelFileProcessor {

  private final ExcelFileHelper excelFileHelper;

  @Autowired
  public ExcelFileProcessorImpl(ExcelFileHelper excelFileHelper) {
    this.excelFileHelper = excelFileHelper;
  }

  @Override
  public void validateExcelFile(MultipartFile file) throws Exception {
    if (Objects.requireNonNull(file.getContentType()).isEmpty()) {
      throw new IOException("Not excel files");
    }
    if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(
        file.getContentType())) {
      throw new IOException("Not excel files");
    }
  }

  @Override
  public List<WarehouseInventoryHistoryTransaction> getHistories(MultipartFile file)
      throws Exception {
    List<WarehouseInventoryHistoryTransaction> histories = new ArrayList<>();
    List<Integer> errorRows = new ArrayList<>();

    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss aa");
    format.setTimeZone(TimeZone.getTimeZone("GMT+7"));
    Workbook workbook = new XSSFWorkbook(file.getInputStream());
    Sheet sheet = workbook.getSheetAt(0);
    Map<String, Integer> keyToColumnMap = new LinkedHashMap<>();


    for (Row row : sheet) {
      if (row.getRowNum() == 0) {
        AtomicInteger count = new AtomicInteger(0);
        for (Cell cell : row) {
          keyToColumnMap.put(cell.getStringCellValue(), count.getAndIncrement());
        }

        continue;
      }

      WarehouseInventoryHistoryTransaction txn = new WarehouseInventoryHistoryTransaction();
      try {
        txn.setWarehouseMerchantCode(row.getCell(keyToColumnMap.get("warehouseMerchantCode")).getStringCellValue());
        txn.setWarehouseItemSku(row.getCell(keyToColumnMap.get("warehouseItemSku")).getStringCellValue());
        txn.setOriginalStock(convertSafely(row.getCell(keyToColumnMap.get("originalStock"))));
        txn.setAvailableStock(convertSafely(row.getCell(keyToColumnMap.get("availableStock"))));
        txn.setTransactionQuantity(convertSafely(row.getCell(keyToColumnMap.get("transactionQuantity"))));
        txn.setWarehouseCode(row.getCell(keyToColumnMap.get("warehouseCode")).getStringCellValue());
        txn.setCustomerLogonId(row.getCell(keyToColumnMap.get("customerLogonId")).getStringCellValue());
        txn.setTransactionDescription(row.getCell(keyToColumnMap.get("transactionDescription")).getStringCellValue());
        txn.setTransactionDate(row.getCell(keyToColumnMap.get("transactionDate")).getStringCellValue());
        txn.setOrderItemId(row.getCell(keyToColumnMap.get("orderItemId")).getStringCellValue());
        txn.setActionKey(row.getCell(keyToColumnMap.get("actionKey")).getStringCellValue());
        txn.setUsedApi(row.getCell(keyToColumnMap.get("usedApi")).getStringCellValue());
        txn.setRequestId(row.getCell(keyToColumnMap.get("REQUEST_ID")).getStringCellValue());
        txn.setChannelId(row.getCell(keyToColumnMap.get("CHANNEL_ID")).getStringCellValue());
        txn.setClientId(row.getCell(keyToColumnMap.get("CLIENT_ID")).getStringCellValue());
        txn.setUsername(row.getCell(keyToColumnMap.get("username")).getStringCellValue());

      } catch (Exception e) {
        errorRows.add(row.getRowNum() + 1);
        continue;
      }

      histories.add(txn);
    }

    if (!errorRows.isEmpty()) {
      log.error("{} rows with errors: {}", errorRows.size(), errorRows);
    }

    return histories;

  }

  private Double convertSafely(Cell cell) {
    try {
      return cell.getNumericCellValue();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public ByteArrayOutputStream generateExcelFileFromMap(Map<String, List<Map<String, String>>> data)
      throws Exception {
    try (Workbook workbook = new XSSFWorkbook()) {
      data.forEach((sheetName, sheetData) -> {
        Sheet sheet = workbook.createSheet("Output " + sheetName);

        Map<String, Integer> keyToColumnMap = new LinkedHashMap<>();
        List<String> columns = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        Map<String, String> headerRow = sheetData.remove(0);
        headerRow.forEach((k, v) -> keyToColumnMap.put(k, count.getAndIncrement()));

        keyToColumnMap.forEach((k, v) -> columns.add(k));
        excelFileHelper.createHeaderRow(sheet.createRow(0), columns);

        for (int j = 0; j < sheetData.size(); j++) {
          excelFileHelper.createDataRow(sheet.createRow(j + 1), keyToColumnMap, sheetData.get(j));
        }
      });

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      return outputStream;
    }
  }
}
