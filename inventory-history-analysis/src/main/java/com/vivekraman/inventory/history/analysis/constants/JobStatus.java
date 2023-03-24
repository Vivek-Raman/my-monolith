package com.vivekraman.inventory.history.analysis.constants;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum JobStatus {
  FILE_BEING_PROCESSED,
  FAILED_TO_PROCESS_FILE,
  ANALYZING,
  COMPLETE,
  ;
}
