package com.vivekraman.terrarium.service.impl;

import com.vivekraman.terrarium.entity.Action;
import com.vivekraman.terrarium.entity.Plant;
import com.vivekraman.terrarium.entity.utils.ActionType;
import com.vivekraman.terrarium.repository.PlantRepository;
import com.vivekraman.terrarium.service.api.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlantServiceImpl implements PlantService {

  @Autowired
  private PlantRepository plantRepo;

  @Override
  public Plant createNewPlantInRoom(Plant toAdd) {
    return plantRepo.save(toAdd);
  }

  @Override
  public List<Plant> getRoomOfUser(UUID userID) {
    List<Plant> plants = new ArrayList<Plant>();
    plantRepo.findPlantsByUserUserID(userID).forEach(plants::add);
    return plants;
  }

  @Override
  public Plant findByID(UUID plantID) {
    return plantRepo.findById(plantID).get();
  }

  @Override
  public void processPendingActions(UUID userID, List<Action> actions) {
    List<Plant> plantsOfUser = new ArrayList<Plant>();
    plantRepo.findPlantsByUserUserID(userID).forEach(plantsOfUser::add);

    for (Action action : actions) {
      if (action.isQueued()) {
        int index = plantsOfUser.indexOf(action.getPlant());
        if (index >= 0) {
          if (action.getActionType() == ActionType.USER_WATERS_PLANT) {
            plantsOfUser.get(index).alterGrowthState(true);
          }
        }
      }
    }

    plantRepo.saveAll(plantsOfUser);
  }
}
