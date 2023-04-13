package com.vivekraman.fix.subscription.dates.service.api;

import com.vivekraman.fix.subscription.dates.model.response.UpdateQueries;

public interface CorrectionService {
  UpdateQueries prepareQueries();
}
