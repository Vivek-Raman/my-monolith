package dev.vivekraman.terrarium.model.responses;

import dev.vivekraman.terrarium.entity.Plant;
import lombok.Getter;

import java.util.List;

@Getter
public class RoomResponse extends BasicResponse{

  private List<Plant> plants;

  public RoomResponse(String _status, List<Plant> plants) {
    super(_status);
    this.plants = plants;
  }
}
