package dev.vivekraman.fix.subscription.dates.service.impl;

import dev.vivekraman.fix.subscription.dates.model.request.FailScheduleQueryParameters;
import dev.vivekraman.fix.subscription.dates.model.request.UpdateQueryParameters;
import dev.vivekraman.fix.subscription.dates.service.api.QueryBuilderService;
import dev.vivekraman.utils.DateHelper;
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
            "updatedBy": "BM-5521",
            "updatedDate": ISODate()
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
          "_id": ObjectId("%s"),
          "status": "NOT_PROCESSED",
        }, {
          "$set": {
            "status": "FAILED",
            "failInfo.code": "UNSPECIFIED",
            "failInfo.message": "BM-5521 Fix",
            "updatedBy": "BM-5521",
            "updatedDate": ISODate()
          }
        });
        db.subscription.updateOne({
          "subscriptionId": "%s"
        }, {
          "$inc": {
            "processedSchedules": 1,
          },
          "$set": {
            "updatedBy": "BM-5521",
            "updatedDate": ISODate()
          },
        });
        """.formatted(parameters.getBundleId(), parameters.getSubscriptionId(),
            DateHelper.buildISO8601DateString(parameters.getPastOrderProcessingDate()),
            parameters.getScheduleId(), parameters.getSubscriptionId())
        .replaceAll("[\n\s]+", " ");
  }
}
