package com.vivekraman.inventory.history.analysis.service.api;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;

public interface ExcelFileHelper {
  void createHeaderRow(Row row, List<String> headers);

  void createDataRow(Row row, Map<String, Integer> keyToColumnMap, Map<String, String> rowData);
}
