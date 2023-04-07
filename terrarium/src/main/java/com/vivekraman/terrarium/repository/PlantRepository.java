package com.vivekraman.terrarium.repository;

import com.vivekraman.terrarium.entity.Plant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlantRepository extends CrudRepository<Plant, UUID> {
  Iterable<Plant> findPlantsByUserUserID(UUID user_userID);
}
