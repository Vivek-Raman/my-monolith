package com.vivekraman.terrarium.controller;

import com.vivekraman.terrarium.model.requests.UserIdentifierRequest;
import com.vivekraman.terrarium.model.responses.BooleanResponse;
import com.vivekraman.terrarium.model.responses.UserAuthenticationResponse;
import com.vivekraman.terrarium.entity.Action;
import com.vivekraman.terrarium.entity.Session;
import com.vivekraman.terrarium.entity.User;
import com.vivekraman.terrarium.service.api.ActionService;
import com.vivekraman.terrarium.service.api.PlantService;
import com.vivekraman.terrarium.service.api.SessionService;
import com.vivekraman.terrarium.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private PlantService plantService;

  @Autowired
  private ActionService actionService;

  @PostMapping("/register")
  public BooleanResponse registerUser(
      @RequestBody UserIdentifierRequest request) {

    return new BooleanResponse(
        "OK",
        userService.registerUser(new User(request))
    );
  }

  @PostMapping("/authenticate")
  public UserAuthenticationResponse authenticateUser(
      @RequestBody UserIdentifierRequest request) {

    User user = userService.authenticateUser(request);
    if (user == null) {
      return new UserAuthenticationResponse("ERROR: Authentication failed", null);
    }

    List<Action> actions = actionService.findByUserID(user.getUserID());
    plantService.processPendingActions(user.getUserID(), actions);
    actionService.resolveAllActionsOfUser(user.getUserID());

    Session oldSession = sessionService.captureUserLogin(user);

    // upsert oldSession timestamp
    return new UserAuthenticationResponse("OK", oldSession);
  }

}
