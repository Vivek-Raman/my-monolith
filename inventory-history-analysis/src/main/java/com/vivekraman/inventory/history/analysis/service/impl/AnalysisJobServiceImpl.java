package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.constants.JobStatus;
import com.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import com.vivekraman.inventory.history.analysis.model.WarehouseInventoryIdentifier;
import com.vivekraman.inventory.history.analysis.repository.AnalysisJobRepository;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisJobService;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisJobServiceImpl implements AnalysisJobService {
  private final AnalysisService analysisService;
  private final AnalysisJobRepository analysisJobRepository;

  @Override
  public AnalysisJob initiateIngest(WarehouseInventoryIdentifier inventory, String fileName) {
    AnalysisJob analysisJob = AnalysisJob.builder()
        .id(inventory.generateIdentifier())
        .startTime(new Date())
        .fileName(fileName)
        .status(JobStatus.FILE_BEING_PROCESSED.name())
        .build();
    return analysisJobRepository.save(analysisJob);
  }

  @Override
  public AnalysisJob initiateAnalysis(WarehouseInventoryIdentifier inventory) {
    analysisService.analyze(inventory.generateIdentifier());
    AnalysisJob job = analysisJobRepository.findById(inventory.generateIdentifier()).orElseThrow();
    job.setStatus(JobStatus.ANALYZING.name());
    return analysisJobRepository.save(job);
  }

  @Override
  public AnalysisJob findByIdentifier(WarehouseInventoryIdentifier inventory) {
    return analysisJobRepository.findById(inventory.generateIdentifier()).orElse(null);
  }
}