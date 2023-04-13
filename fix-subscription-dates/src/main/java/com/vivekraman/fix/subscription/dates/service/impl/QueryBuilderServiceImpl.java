package com.vivekraman.fix.subscription.dates.service.impl;

import com.vivekraman.fix.subscription.dates.model.request.FailScheduleQueryParameters;
import com.vivekraman.fix.subscription.dates.model.request.UpdateQueryParameters;
import com.vivekraman.fix.subscription.dates.service.api.QueryBuilderService;
import com.vivekraman.utils.DateHelper;
import org.springframework.stereotype.Service;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {
  @Override
  public String buildUpdateQuery(UpdateQueryParameters parameters) {
    return """
        db.subscription_schedule.updateOne({
          "bundleId": "%s",
          "subscriptionId": "%s",
          "orderProcessingDate": ISODate("%s"),
          "_id": ObjectId("%s")
        }, {
          "$set": {
            "orderProcessingDate": ISODate("%s"),
          }
        });
        """.formatted(parameters.getBundleId(), parameters.getSubscriptionId(),
            DateHelper.buildISO8601DateString(parameters.getIncorrectOrderProcessingDate()),
            parameters.getScheduleId(),
            DateHelper.buildISO8601DateString(parameters.getExpectedNextProcessingDate()))
        .replaceAll("[\n\s]+", " ");
  }

  @Override
  public String buildFailQuery(FailScheduleQueryParameters parameters) {
    return """
        db.subscription_schedule.updateOne({
          "bundleId": "%s",
          "subscriptionId": "%s",
          "orderProcessingDate": ISODate("%s"),
          "_id": ObjectId("%s")
        }, {
          "$set": {
            "status": "FAILED",
            "failInfo": {
              TODO
            }
          }
        });
        """.formatted(parameters.getBundleId(), parameters.getSubscriptionId(),
            DateHelper.buildISO8601DateString(parameters.getPastOrderProcessingDate()),
            parameters.getScheduleId())
        .replaceAll("[\n\s]+", " ");
  }
}
