package com.vivekraman.fix.subscription.dates.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vivekraman.fix.subscription.dates.constants.SubscriptionFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateQueryParameters implements Serializable {
  @Serial
  private static final long serialVersionUID = -8070881245298613081L;

  private String bundleId;
  private String subscriptionId;
  private Date incorrectOrderProcessingDate;
  private String scheduleId;
  private Date expectedNextProcessingDate;
}
