package com.vivekraman.terrarium.model.requests;

import com.vivekraman.terrarium.entity.utils.ActionType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddActionRequest {
  private UUID plantID;
  private ActionType actionType;
}
