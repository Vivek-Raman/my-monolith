package dev.vivekraman.fix.subscription.dates.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateQueries implements Serializable {
  @Serial
  private static final long serialVersionUID = -2038180324467325591L;

  private List<String> dateUpdateQueries;
  private List<String> failScheduleQueries;
  private Integer untouchedSchedules;
}
