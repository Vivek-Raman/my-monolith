package com.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "rule_state")
@Table(name = "rule_state")
public class RuleState implements Serializable {
  @Serial
  private static final long serialVersionUID = -6180156181212120744L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column("test_key")
  private String testKey;
}
