package dev.vivekraman.terrarium.repository;

import dev.vivekraman.terrarium.entity.Session;
import dev.vivekraman.terrarium.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {
  Optional<Session> findByUser(User user);
}
