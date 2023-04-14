package dev.vivekraman.inventory.history.analysis.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vivekraman.inventory.history.analysis.entity.RuleState;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

public abstract class RuleMetadata implements Serializable {
  @Serial
  private static final long serialVersionUID = 81954528748427981L;

  protected static <T> T readFromRuleState(ObjectMapper mapper, RuleState state,
      Class<T> clazz) throws Exception {
    if (StringUtils.isBlank(state.getStateMetadataJson())) return null;
    return mapper.readValue(state.getStateMetadataJson(), clazz);
  }

  public void writeToRuleState(ObjectMapper mapper, RuleState state)
      throws Exception {
    state.setStateMetadataJson(mapper.writeValueAsString(this));
  }
}
