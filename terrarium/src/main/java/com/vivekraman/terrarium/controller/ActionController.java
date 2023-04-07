package com.vivekraman.terrarium.controller;

import com.vivekraman.terrarium.model.requests.AddActionRequest;
import com.vivekraman.terrarium.model.responses.BooleanResponse;
import com.vivekraman.terrarium.entity.Plant;
import com.vivekraman.terrarium.entity.utils.ActionType;
import com.vivekraman.terrarium.service.api.ActionService;
import com.vivekraman.terrarium.service.api.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

  @Autowired
  private PlantService plantService;

  @Autowired
  private ActionService actionService;

  @PostMapping("/add")
  public BooleanResponse addAction(
      @RequestBody AddActionRequest request) {

    if (request.getActionType() == ActionType.NULL) {
      return new BooleanResponse("ERROR: NULL action received.", false);
    }
    if (request.getActionType() == ActionType.USER_WATERS_PLANT) {
      actionService.addAction(plantService.findByID(request.getPlantID()), request.getActionType());
      return new BooleanResponse("OK: Queued Watering Plant " + request.getPlantID(), true);
    }
    if (request.getActionType() == ActionType.USER_TRIMS_PLANT) {
      Plant plant = plantService.findByID(request.getPlantID());
      plant.alterGrowthState(false);
      plantService.createNewPlantInRoom(plant);
    }

    return new BooleanResponse("OK", true);
  }
}
