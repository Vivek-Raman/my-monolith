package com.vivekraman.terrarium.model.responses;

import lombok.Getter;

@Getter
public class BooleanResponse extends BasicResponse {
  private boolean data;

  public BooleanResponse(String status, boolean data) {
    super(status);
    this.data = data;
  }
}
