package dev.vivekraman.terrarium.entity;


import dev.vivekraman.terrarium.constants.ApiPath;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = ApiPath.MODULE_NAME + "_app_user_session")
public class Session {
  @Id
  @Column(name = "session_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID sessionID;

  @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "timestamp")
  private Date lastLoginTimestamp;

  public Session(User user) {
    this.user = user;
    lastLoginTimestamp = new Date();
  }

  public void relogTimestamp() {
    lastLoginTimestamp = new Date();
  }
}
