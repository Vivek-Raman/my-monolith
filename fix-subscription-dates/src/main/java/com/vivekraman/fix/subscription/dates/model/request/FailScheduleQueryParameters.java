package com.vivekraman.fix.subscription.dates.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FailScheduleQueryParameters implements Serializable {

  @Serial
  private static final long serialVersionUID = 8786433005259647622L;
  private String bundleId;
  private String subscriptionId;
  private Date pastOrderProcessingDate;
  private String scheduleId;
}
