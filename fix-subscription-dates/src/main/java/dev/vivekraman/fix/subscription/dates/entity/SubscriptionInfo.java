package dev.vivekraman.fix.subscription.dates.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.vivekraman.fix.subscription.dates.constants.SubscriptionFrequency;
import dev.vivekraman.utils.DateHelper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "bundle_info")
@Table(name = "bundle_info")
public class SubscriptionInfo implements Serializable {
  @Serial
  private static final long serialVersionUID = 8811969204181719919L;

  @Id
  private String subscriptionId;
  private String bundleId;
  private Date correctEarliestOrderProcessingDate;
  private SubscriptionFrequency subscriptionFrequency;
  @Builder.Default private Integer scheduleCounter = 0;

  @SneakyThrows
  public void setCorrectEarliestOrderProcessingDate(String correctEarliestOrderProcessingDate) {
    this.correctEarliestOrderProcessingDate =
        DateHelper.parseISO8601DateString(correctEarliestOrderProcessingDate);
  }

  public void incrementScheduleCounter() {
    ++this.scheduleCounter;
  }
}
