package com.vivekraman.inventory.history.analysis.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import com.vivekraman.inventory.history.analysis.service.api.ExcelFileProcessor;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryHistoryTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
  @Qualifier("objectMapperJKT")
  private final ObjectMapper objectMapper;
  private final ExcelFileProcessor excelFileProcessor;

  @Autowired
  public AnalysisServiceImpl(ExcelFileProcessor excelFileProcessor, ObjectMapper objectMapper) {
    this.excelFileProcessor = excelFileProcessor;
    this.objectMapper = objectMapper;
  }

  @Override
  public String getOutputFilename(String oldFileName, String newFileName) {
    return oldFileName.replace(".xlsx", "").replace('_', ' ')
        .replace("-X-Inv-vs-Stockholm Hourly Table", "").replace(",", "") + " to "
        + newFileName.replace(".xlsx", "").replace('_', ' ')
        .replace("-X-Inv-vs-Stockholm Hourly Table", "").replace(",", "") + ".xlsx";
  }

  @Override
  public ByteArrayOutputStream filterTransactionHistory(MultipartFile txnHistoryFile)
      throws Exception {
    this.excelFileProcessor.validateExcelFile(txnHistoryFile);
    List<WarehouseInventoryHistoryTransaction> txns =
        this.excelFileProcessor.getHistories(txnHistoryFile);

    // TODO: insert filtering logic on txns

    return this.excelFileProcessor.generateExcelFileFromMap(
        this.objectMapper.readValue(this.objectMapper.writeValueAsBytes(txns),
            objectMapper.getTypeFactory().constructCollectionType(ArrayList.class,
                objectMapper.getTypeFactory()
                    .constructMapType(LinkedHashMap.class, String.class, String.class))));
  }
}
