package dev.vivekraman.terrarium.repository;

import dev.vivekraman.terrarium.entity.Plant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlantRepository extends CrudRepository<Plant, UUID> {
  Iterable<Plant> findPlantsByUserUserID(UUID user_userID);
}
