package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.service.api.ExcelFileHelper;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExcelFileHelperImpl implements ExcelFileHelper {
  @Override
  public void createHeaderRow(Row row, List<String> headers) {
    for (int i = 0; i < headers.size(); i++) {
      row.createCell(i).setCellValue(headers.get(i));
    }
  }

  @Override
  public void createDataRow(Row row, Map<String, Integer> keyToColumnMap,
      Map<String, String> rowData) {
    rowData.forEach((k, v) -> row.createCell(keyToColumnMap.get(k)).setCellValue(v));
  }
}
