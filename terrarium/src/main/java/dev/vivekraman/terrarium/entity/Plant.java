package dev.vivekraman.terrarium.entity;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "plant")
public class Plant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "plant_id")
  private UUID plantID;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "species_id")
  private int speciesID;

  @Column(name = "date_of_planting")
  private Date dateOfPlanting;

  @Column(name = "posX")
  private float positionX;

  @Column(name = "posY")
  private float positionY;

  @Column(name = "posZ")
  private float positionZ;

  @Column(name = "rotX")
  private float rotationX;

  @Column(name = "rotY")
  private float rotationY;

  @Column(name = "rotZ")
  private float rotationZ;

  @Column(name = "unwatered_day_count")
  private int unwateredDayCount;

  @Column(name = "growth_state")
  private int growthState;

  public Plant(User user, int speciesID, float positionX, float positionY, float positionZ, float rotationX, float rotationY, float rotationZ, int growthState) {
    this.user = user;
    this.speciesID = speciesID;
    this.dateOfPlanting = new Date();
    this.positionX = positionX;
    this.positionY = positionY;
    this.positionZ = positionZ;
    this.rotationX = rotationX;
    this.rotationY = rotationY;
    this.rotationZ = rotationZ;
    this.unwateredDayCount = 0;
    this.growthState = growthState;
  }

  public void alterGrowthState(boolean positive) {
    this.growthState = positive ? this.growthState + 1 : this.growthState - 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Plant plant = (Plant) o;
    return plantID.equals(plant.plantID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plantID);
  }
}
