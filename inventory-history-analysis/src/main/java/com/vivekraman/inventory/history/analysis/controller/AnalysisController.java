package com.vivekraman.inventory.history.analysis.controller;

import com.vivekraman.constants.ApiPath;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping(ApiPath.BASE_URL)
public class AnalysisController implements ApiPath {

  private final AnalysisService analysisService;

  @Autowired
  public AnalysisController(AnalysisService analysisService) {
    this.analysisService = analysisService;
  }

  @PostMapping(path = "/filter-txn-history", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Resource> filterTransactionHistory(
      @RequestPart("txnHistoryFile") MultipartFile txnHistoryFile) throws Exception {
    ByteArrayOutputStream response = analysisService.filterTransactionHistory(txnHistoryFile);
    String outputFilename = txnHistoryFile.getOriginalFilename() + "_filtered";
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + outputFilename)
        .contentLength(response.size())
        .body(new ByteArrayResource(response.toByteArray()));
  }
}
