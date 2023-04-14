package dev.vivekraman.terrarium.service.api;

import dev.vivekraman.terrarium.model.requests.UserIdentifierRequest;
import dev.vivekraman.terrarium.entity.User;

import java.util.UUID;

public interface UserService {

  boolean registerUser(User toRegister);

  User authenticateUser(UserIdentifierRequest toAuthenticate);

  User findByID(UUID userID);
}
