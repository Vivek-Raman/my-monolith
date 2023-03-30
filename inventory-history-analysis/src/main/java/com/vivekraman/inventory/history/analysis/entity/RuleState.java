package com.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

  @Column("rule_id")
  private String ruleId;

  @Column("inventory_id")
  private String inventoryIdentifier;

  @Column("analysis_job_fk")
  @ManyToOne(targetEntity = AnalysisJob.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JsonBackReference
  private AnalysisJob analysisJob;

  @Column("state_key")
  private String stateKey;

  @Column
  private String stateMetadataJson;

  @Column
  @Builder.Default
  @JsonIgnore
  private Boolean ignoreEndState = false;
}
