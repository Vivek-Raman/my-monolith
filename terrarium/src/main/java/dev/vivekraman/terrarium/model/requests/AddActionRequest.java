package dev.vivekraman.terrarium.model.requests;

import dev.vivekraman.terrarium.entity.utils.ActionType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddActionRequest {
  private UUID plantID;
  private ActionType actionType;
}
