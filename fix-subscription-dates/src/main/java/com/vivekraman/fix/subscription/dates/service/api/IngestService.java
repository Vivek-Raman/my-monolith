package com.vivekraman.fix.subscription.dates.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IngestService {
  void readSubscriptionInfoFile(MultipartFile file) throws IOException;

  void readScheduleInfoFile(MultipartFile scheduleInfoFile) throws IOException;
}
