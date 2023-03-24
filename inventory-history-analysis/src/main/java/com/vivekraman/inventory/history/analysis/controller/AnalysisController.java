package com.vivekraman.inventory.history.analysis.controller;

import com.vivekraman.inventory.history.analysis.constants.ApiPath;
import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryIdentifier;
import com.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisJobService;
import com.vivekraman.inventory.history.analysis.service.api.FileIngestService;
import com.vivekraman.model.Response;
import com.vivekraman.model.ResponseList;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiPath.BASE_URL)
@RequiredArgsConstructor
public class AnalysisController implements ApiPath {
  private final FileIngestService fileIngestService;
  private final AnalysisJobService analysisJobService;
  private final WarehouseInventoryHistoryTransactionRepository txnRepository;

  @PostMapping(path = PROCESS_AUDIT_LOGS + INITIATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Response<AnalysisJob> initiateAnalysis(
      WarehouseInventoryIdentifier inventory,
      @RequestPart("txnHistoryFile") MultipartFile txnHistoryFile) throws Exception {
    AnalysisJob job = this.analysisJobService.initiateIngest(inventory, txnHistoryFile.getName());
    this.fileIngestService.ingestHistoryLogFile(job, txnHistoryFile);
    job = this.analysisJobService.initiateAnalysis(inventory);
    return Response.of(job);
  }

  @GetMapping(path = PROCESS_AUDIT_LOGS + CHECK_STATUS)
  public Response<AnalysisJob> checkProcessStatus(WarehouseInventoryIdentifier inventory)
      throws Exception {
    return Response.of(this.analysisJobService.findByIdentifier(inventory));
  }

  @GetMapping(path = "/check-db")
  public ResponseList<WarehouseInventoryHistoryTransaction> checkDB(@RequestParam(required = false) String id) {
    Page<WarehouseInventoryHistoryTransaction> txns = txnRepository.findByUniqueIdStartsWith(
        StringUtils.defaultString(id, "1"), PageRequest.of(0, 15));
    return Response.of(txns);
  }
}
