package com.vivekraman.terrarium.entity;

import com.vivekraman.terrarium.entity.utils.ActionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "app_user_action")
public class Action {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "action_id")
  private UUID actionID;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "plant_id")
  private Plant plant;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "action_type")
  private ActionType actionType;

  @Column(name = "is_queued")
  private boolean isQueued;

  public Action(Plant plant, ActionType actionType) {
    this.user = plant.getUser();
    this.plant = plant;
    this.actionType = actionType;
    this.isQueued = true;
  }
}
