package dev.vivekraman.fix.subscription.dates.service.impl;

import dev.vivekraman.fix.subscription.dates.constants.SubscriptionFrequency;
import dev.vivekraman.fix.subscription.dates.entity.ScheduleInfo;
import dev.vivekraman.fix.subscription.dates.entity.SubscriptionInfo;
import dev.vivekraman.fix.subscription.dates.model.request.FailScheduleQueryParameters;
import dev.vivekraman.fix.subscription.dates.model.request.UpdateQueryParameters;
import dev.vivekraman.fix.subscription.dates.model.response.UpdateQueries;
import dev.vivekraman.fix.subscription.dates.repository.ScheduleInfoRepository;
import dev.vivekraman.fix.subscription.dates.repository.SubscriptionInfoRepository;
import dev.vivekraman.fix.subscription.dates.service.api.CorrectionService;
import dev.vivekraman.fix.subscription.dates.service.api.QueryBuilderService;
import dev.vivekraman.utils.DateHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorrectionServiceImpl implements CorrectionService {
  private final QueryBuilderService queryBuilderService;
  private final ScheduleInfoRepository scheduleInfoRepository;
  private final SubscriptionInfoRepository subscriptionInfoRepository;

  @Override
  public UpdateQueries prepareQueries() {
    List<String> updateQueries = new ArrayList<>();
    List<String> failQueries = new ArrayList<>();
    int untouchedScheduleCount = 0;

    List<ScheduleInfo> toCorrect = this.scheduleInfoRepository.findAll();
    for (ScheduleInfo scheduleInfo : toCorrect) {
      SubscriptionInfo subscriptionInfo = this.subscriptionInfoRepository.findBySubscriptionId(
          scheduleInfo.getSubscriptionId());

      Date incorrectDate = scheduleInfo.getOrderProcessingDate();
      Date correctDate = determineDate(
          subscriptionInfo.getCorrectEarliestOrderProcessingDate(),
          subscriptionInfo.getScheduleCounter(),
          subscriptionInfo.getSubscriptionFrequency());

      if (incorrectDate.getTime() == correctDate.getTime()) {
        log.warn("Schedule {} of Subscription {} already matches the expected date! {}",
            scheduleInfo.getScheduleId(), scheduleInfo.getSubscriptionId(),
            DateHelper.buildISO8601DateString(scheduleInfo.getOrderProcessingDate()));
        untouchedScheduleCount++;
        subscriptionInfo.incrementScheduleCounter();
        subscriptionInfo = this.subscriptionInfoRepository.save(subscriptionInfo);
        continue;
      }

      if (incorrectDate.before(new Date())) {
        log.warn("Schedule {} of Subscription {} is before today! {}",
            scheduleInfo.getScheduleId(), scheduleInfo.getSubscriptionId(),
            DateHelper.buildISO8601DateString(scheduleInfo.getOrderProcessingDate()));
        String query = this.queryBuilderService.buildFailQuery(FailScheduleQueryParameters.builder()
            .bundleId(subscriptionInfo.getBundleId())
            .subscriptionId(scheduleInfo.getSubscriptionId())
            .scheduleId(scheduleInfo.getScheduleId())
            .pastOrderProcessingDate(incorrectDate)
            .build());
        failQueries.add(query);
        continue;
      }

      subscriptionInfo.incrementScheduleCounter();
      subscriptionInfo = this.subscriptionInfoRepository.save(subscriptionInfo);

      UpdateQueryParameters queryParams = UpdateQueryParameters.builder()
          .bundleId(subscriptionInfo.getBundleId())
          .subscriptionId(scheduleInfo.getSubscriptionId())
          .scheduleId(scheduleInfo.getScheduleId())
          .incorrectOrderProcessingDate(incorrectDate)
          .expectedNextProcessingDate(correctDate)
          .build();
      String query = this.queryBuilderService.buildUpdateQuery(queryParams);
      updateQueries.add(query);
    }

    return UpdateQueries.builder()
        .dateUpdateQueries(updateQueries)
        .failScheduleQueries(failQueries)
        .untouchedSchedules(untouchedScheduleCount)
        .build();
  }

  private Date determineDate(Date date, int nCycle,
      SubscriptionFrequency subscriptionFrequency) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JKT"));
    calendar.setTime(date);
//    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    if (subscriptionFrequency.getCalendarField() != 0) {
      calendar.add(subscriptionFrequency.getCalendarField(),
          subscriptionFrequency.getCalendarAmount() * nCycle);
    }

    return calendar.getTime();
  }
}
