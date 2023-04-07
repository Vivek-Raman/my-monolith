package com.vivekraman.terrarium.model.responses;

import com.vivekraman.terrarium.entity.Session;
import lombok.Getter;

@Getter
public class UserAuthenticationResponse extends BasicResponse {
  private Session data;

  public UserAuthenticationResponse(String status, Session data) {
    super(status);
    this.data = data;
  }
}
