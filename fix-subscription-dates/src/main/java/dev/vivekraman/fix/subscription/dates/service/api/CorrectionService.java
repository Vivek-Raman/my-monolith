package dev.vivekraman.fix.subscription.dates.service.api;

import dev.vivekraman.fix.subscription.dates.model.response.UpdateQueries;

public interface CorrectionService {
  UpdateQueries prepareQueries();
}
