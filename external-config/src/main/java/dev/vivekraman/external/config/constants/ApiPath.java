package dev.vivekraman.external.config.constants;

public interface ApiPath extends dev.vivekraman.constants.ApiPath {
  String MODULE_NAME = "external-config";
  String BASE_URL = dev.vivekraman.constants.ApiPath.BASE_URL + SLASH + MODULE_NAME;

  String FIND = "/find";
  String UPSERT = "/upsert";
}
