package com.vivekraman.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateHelper {
  public static String buildISO8601DateString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    formatter.setTimeZone(TimeZone.getTimeZone("JKT"));
    return formatter.format(date);
  }

  public static Date parseISO8601DateString(String dateString) throws ParseException {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      formatter.setTimeZone(TimeZone.getTimeZone("JKT"));
      return formatter.parse(dateString);
    } catch (ParseException ignored) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      formatter.setTimeZone(TimeZone.getTimeZone("JKT"));
      return formatter.parse(dateString);
    }
  }
}
