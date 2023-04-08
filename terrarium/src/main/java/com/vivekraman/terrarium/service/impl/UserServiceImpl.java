package com.vivekraman.terrarium.service.impl;

import com.vivekraman.terrarium.model.requests.UserIdentifierRequest;
import com.vivekraman.terrarium.entity.User;
import com.vivekraman.terrarium.repository.UserRepository;
import com.vivekraman.terrarium.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public boolean registerUser(User toRegister) {

    Optional<List<User>> data = userRepo.findByUserName(toRegister.getUserName());
    if (data.isPresent() && data.get().size() > 0) {
      return false;
    }

    userRepo.save(toRegister);
    return true;
  }

  @Override
  public User authenticateUser(UserIdentifierRequest toAuthenticate) {
    Optional<List<User>> data = userRepo.findByUserName(toAuthenticate.getUserName());
    if (!data.isPresent()) return null;

    List<User> users = data.get();
    if (users.size() <= 0) {
      return null;
    }

    if (users.size() > 1) {
      throw new IllegalStateException("Multiple users found with same username: " + toAuthenticate.getUserName());
    }

    if (!users.get(0).getPassword().equals(toAuthenticate.getPassword())) {
      return null;
    }

    return users.get(0);
  }

  @Override
  public User findByID(UUID userID) {
    return userRepo.findById(userID).get();
  }
}
