package com.vivekraman.inventory.history.analysis.constants;

public interface ApiPath extends com.vivekraman.constants.ApiPath {
  String MODULE_NAME = "inventory-history-analysis";
  String BASE_URL = com.vivekraman.constants.ApiPath.BASE_URL + SLASH + MODULE_NAME;

  String PROCESS_AUDIT_LOGS = "/process-audit-logs";
  String INITIATE = "/initiate";
  String CHECK_STATUS = "/check-status";
}
