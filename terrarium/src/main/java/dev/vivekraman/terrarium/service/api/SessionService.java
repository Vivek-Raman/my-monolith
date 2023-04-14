package dev.vivekraman.terrarium.service.api;

import dev.vivekraman.terrarium.entity.Session;
import dev.vivekraman.terrarium.entity.User;

public interface SessionService {

  Session captureUserLogin(User user);
}
