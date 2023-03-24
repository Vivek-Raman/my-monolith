package com.vivekraman.inventory.history.analysis.constants;

public interface ApiPath extends com.vivekraman.constants.ApiPath {
  String BASE_URL = com.vivekraman.constants.ApiPath.BASE_URL + "/inventory-history-analysis";

  String PROCESS_AUDIT_LOGS = "/process-audit-logs";
  String INITIATE = "/initiate";
  String CHECK_STATUS = "/check-status";
}
