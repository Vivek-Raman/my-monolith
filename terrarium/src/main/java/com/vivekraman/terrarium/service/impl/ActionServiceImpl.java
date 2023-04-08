package com.vivekraman.terrarium.service.impl;

import com.vivekraman.terrarium.entity.Action;
import com.vivekraman.terrarium.entity.Plant;
import com.vivekraman.terrarium.entity.utils.ActionType;
import com.vivekraman.terrarium.repository.ActionRepository;
import com.vivekraman.terrarium.service.api.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {

  @Autowired
  private ActionRepository actionRepo;

  @Override
  public void addAction (Plant plant, ActionType actionType) {
    Optional<Action> data = actionRepo.findActionByPlantPlantID(plant.getPlantID());
    if (data.isPresent()) {
      Action action = data.get();
      action.setActionType(actionType);
      action.setQueued(true);
      actionRepo.save(action);
    } else {
      actionRepo.save(new Action(plant, actionType));
    }
  }

  @Override
  public List<Action> findByUserID(UUID userID) {
    return actionRepo.findActionsByUserUserID(userID);
  }

  @Override
  public void resolveAllActionsOfUser(UUID userID) {
    List<Action> actions = findByUserID(userID);
    actions.forEach(action -> action.setQueued(false));
    actionRepo.saveAll(actions);
  }
}
