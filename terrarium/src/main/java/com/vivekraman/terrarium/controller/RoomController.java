package com.vivekraman.terrarium.controller;

import com.vivekraman.terrarium.model.requests.CreateNewPlantInRoomRequest;
import com.vivekraman.terrarium.model.responses.RoomResponse;
import com.vivekraman.terrarium.entity.Plant;
import com.vivekraman.terrarium.service.api.PlantService;
import com.vivekraman.terrarium.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/room")
public class RoomController {

  @Autowired
  private PlantService plantService;

  @Autowired
  private UserService userService;

  @PostMapping("/add")
  public RoomResponse createNewPlantInRoom(
      @RequestBody CreateNewPlantInRoomRequest request
  ) {
    Plant toAdd = new Plant(
        userService.findByID(request.getUserID()),
        request.getSpeciesID(),
        request.getPositionX(),
        request.getPositionY(),
        request.getPositionZ(),
        request.getRotationX(),
        request.getRotationY(),
        request.getRotationZ(),
        request.getGrowthState()
    );
    plantService.createNewPlantInRoom(toAdd);
    return new RoomResponse(
        "OK",
        plantService.getRoomOfUser(request.getUserID())
    );
  }

  @GetMapping("/{userID}")
  public RoomResponse getRoomOfUser(
      @PathVariable("userID") UUID userID
  ) {
    return new RoomResponse(
        "OK",
        plantService.getRoomOfUser(userID)
    );
  }
}
