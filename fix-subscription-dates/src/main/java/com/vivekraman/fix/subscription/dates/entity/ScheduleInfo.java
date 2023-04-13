package com.vivekraman.fix.subscription.dates.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vivekraman.utils.DateHelper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "schedule_info")
@Table(name = "schedule_info")
public class ScheduleInfo implements Serializable {
  @Serial
  private static final long serialVersionUID = 3494554428825756647L;

  @Id
  private String scheduleId;
  private String subscriptionId;
  private Date orderProcessingDate;

  @SneakyThrows
  public void setOrderProcessingDate(String orderProcessingDate) {
    this.orderProcessingDate = DateHelper.parseISO8601DateString(orderProcessingDate);
  }
}
