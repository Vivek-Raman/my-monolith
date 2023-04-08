package com.vivekraman.terrarium.service.api;

import com.vivekraman.terrarium.model.requests.UserIdentifierRequest;
import com.vivekraman.terrarium.entity.User;
import com.vivekraman.terrarium.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UserService {

  boolean registerUser(User toRegister);

  User authenticateUser(UserIdentifierRequest toAuthenticate);

  User findByID(UUID userID);
}
