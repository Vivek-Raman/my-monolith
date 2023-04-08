package com.vivekraman.terrarium.repository;

import com.vivekraman.terrarium.entity.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionRepository extends CrudRepository<Action, UUID> {
  public List<Action> findActionsByUserUserID(UUID user_userID);
  public Optional<Action> findActionByPlantPlantID(UUID plant_plantID);
}
