package com.vivekraman.terrarium.service.api;

import com.vivekraman.terrarium.entity.Session;
import com.vivekraman.terrarium.entity.User;
import org.springframework.stereotype.Service;

public interface SessionService {

  Session captureUserLogin(User user);
}
