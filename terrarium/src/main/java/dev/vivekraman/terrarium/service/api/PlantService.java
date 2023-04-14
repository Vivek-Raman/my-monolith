package dev.vivekraman.terrarium.service.api;

import dev.vivekraman.terrarium.entity.Action;
import dev.vivekraman.terrarium.entity.Plant;

import java.util.List;
import java.util.UUID;

public interface PlantService {
  Plant createNewPlantInRoom(Plant toAdd);

  List<Plant> getRoomOfUser(UUID userID);

  Plant findByID(UUID plantID);

  void processPendingActions(UUID userID, List<Action> actions);
}
