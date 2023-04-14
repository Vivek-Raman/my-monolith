package dev.vivekraman.fix.subscription.dates.constants;

public interface ApiPath extends dev.vivekraman.constants.ApiPath {
  String MODULE_NAME = "fix-subscription-dates";
  String BASE_URL = dev.vivekraman.constants.ApiPath.BASE_URL + SLASH + MODULE_NAME;
  String PROCESS = "/process";
}
