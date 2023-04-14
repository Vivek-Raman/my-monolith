package dev.vivekraman.fix.subscription.dates.service.api;

import dev.vivekraman.fix.subscription.dates.model.request.FailScheduleQueryParameters;
import dev.vivekraman.fix.subscription.dates.model.request.UpdateQueryParameters;

public interface QueryBuilderService {
  String buildUpdateQuery(UpdateQueryParameters parameters);
  String buildFailQuery(FailScheduleQueryParameters parameters);
}
