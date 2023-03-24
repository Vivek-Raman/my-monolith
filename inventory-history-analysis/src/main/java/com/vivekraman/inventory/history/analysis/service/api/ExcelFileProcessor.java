package com.vivekraman.inventory.history.analysis.service.api;

import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryHistoryTransaction;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface ExcelFileProcessor {

  void validateExcelFile(MultipartFile file) throws Exception;

  List<WarehouseInventoryHistoryTransaction> getHistories(MultipartFile file) throws Exception;

  ByteArrayOutputStream generateExcelFileFromMap(Map<String, List<Map<String, String>>> data) throws Exception;
}
