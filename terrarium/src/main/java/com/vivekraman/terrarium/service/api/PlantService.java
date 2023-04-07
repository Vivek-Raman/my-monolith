package com.vivekraman.terrarium.service.api;

import com.vivekraman.terrarium.entity.Action;
import com.vivekraman.terrarium.entity.Plant;
import com.vivekraman.terrarium.entity.User;

import java.util.List;
import java.util.UUID;

public interface PlantService {
  Plant createNewPlantInRoom(Plant toAdd);

  List<Plant> getRoomOfUser(UUID userID);

  Plant findByID(UUID plantID);

  void processPendingActions(UUID userID, List<Action> actions);
}
