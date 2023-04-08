package com.vivekraman.external.config.constants;

public interface ApiPath extends com.vivekraman.constants.ApiPath {
  String MODULE_NAME = "external-config";
  String BASE_URL = com.vivekraman.constants.ApiPath.BASE_URL + SLASH + MODULE_NAME;

  String FIND = "/find";
  String UPSERT = "/upsert";
}
