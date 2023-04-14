package dev.vivekraman.terrarium.repository;

import dev.vivekraman.terrarium.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
  Optional<List<User>> findByUserName(String userName);
}
