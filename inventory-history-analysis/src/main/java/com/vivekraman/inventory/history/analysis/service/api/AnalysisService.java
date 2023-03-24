package com.vivekraman.inventory.history.analysis.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface AnalysisService {
  String getOutputFilename(String oldFileName, String newFileName);

  ByteArrayOutputStream filterTransactionHistory(MultipartFile txnHistoryFile) throws Exception;
}
