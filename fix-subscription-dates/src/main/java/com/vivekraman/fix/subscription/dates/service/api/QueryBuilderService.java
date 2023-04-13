package com.vivekraman.fix.subscription.dates.service.api;

import com.vivekraman.fix.subscription.dates.model.request.FailScheduleQueryParameters;
import com.vivekraman.fix.subscription.dates.model.request.UpdateQueryParameters;

public interface QueryBuilderService {
  String buildUpdateQuery(UpdateQueryParameters parameters);
  String buildFailQuery(FailScheduleQueryParameters parameters);
}
