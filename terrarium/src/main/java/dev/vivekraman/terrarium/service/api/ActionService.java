package dev.vivekraman.terrarium.service.api;

import dev.vivekraman.terrarium.entity.Action;
import dev.vivekraman.terrarium.entity.Plant;
import dev.vivekraman.terrarium.entity.utils.ActionType;

import java.util.List;
import java.util.UUID;

public interface ActionService {

  public void addAction(Plant plant, ActionType actionType);

  public List<Action> findByUserID(UUID userID);

  void resolveAllActionsOfUser(UUID userID);
}
