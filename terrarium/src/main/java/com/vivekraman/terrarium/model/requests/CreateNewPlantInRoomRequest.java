package com.vivekraman.terrarium.model.requests;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateNewPlantInRoomRequest {
  private UUID userID;
  private int speciesID;
  private float positionX;
  private float positionY;
  private float positionZ;
  private float rotationX;
  private float rotationY;
  private float rotationZ;
  private int growthState;
}
