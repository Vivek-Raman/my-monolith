package com.vivekraman.terrarium.entity;

import com.vivekraman.terrarium.model.requests.UserIdentifierRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID userID;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "score")
  private int score;

  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
    this.score = 0;
  }

  public User(UserIdentifierRequest request) {
    this.userName = request.getUserName();
    this.password = request.getPassword();
    this.score = 0;
  }

}
