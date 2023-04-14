package dev.vivekraman.terrarium.model.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public abstract class BasicResponse implements Serializable {
  private String _status;
}
