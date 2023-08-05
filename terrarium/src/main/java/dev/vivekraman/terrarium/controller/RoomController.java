package dev.vivekraman.terrarium.controller;

import dev.vivekraman.terrarium.constants.ApiPath;
import dev.vivekraman.terrarium.model.requests.CreateNewPlantInRoomRequest;
import dev.vivekraman.terrarium.model.responses.RoomResponse;
import dev.vivekraman.terrarium.entity.Plant;
import dev.vivekraman.terrarium.service.api.PlantService;
import dev.vivekraman.terrarium.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.BASE_URL + "/room")
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
