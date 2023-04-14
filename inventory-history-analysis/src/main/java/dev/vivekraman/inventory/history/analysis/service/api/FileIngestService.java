package dev.vivekraman.inventory.history.analysis.service.api;

import dev.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import org.springframework.web.multipart.MultipartFile;

public interface FileIngestService {
  void ingestHistoryLogFile(AnalysisJob job, MultipartFile file) throws Exception;
}
