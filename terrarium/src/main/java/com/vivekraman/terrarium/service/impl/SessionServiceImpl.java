package com.vivekraman.terrarium.service.impl;

import com.vivekraman.terrarium.entity.Session;
import com.vivekraman.terrarium.entity.User;
import com.vivekraman.terrarium.repository.SessionRepository;
import com.vivekraman.terrarium.service.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

  @Autowired
  private SessionRepository sessionRepo;

  @Override
  public Session captureUserLogin(User user) {
    Optional<Session> data = sessionRepo.findByUser(user);
    if (!data.isPresent() || "".equals(data.get().getSessionID().toString())) {
      return sessionRepo.save(new Session(user));
    }

    // set last login to now
    Session session = data.get();
    session.relogTimestamp();
    return sessionRepo.save(session);
  }
}
