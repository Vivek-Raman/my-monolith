package com.vivekraman.fix.subscription.dates.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;

@Getter
@AllArgsConstructor
public enum SubscriptionFrequency {
  MONTHLY(Calendar.MONTH, 1),
  WEEKLY(Calendar.DAY_OF_MONTH, 7);

  private final int calendarField;
  private final int calendarAmount;
}
