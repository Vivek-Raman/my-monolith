package com.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "analysis_job")
@Table(name = "analysis_job")
public class AnalysisJob implements Serializable {
  @Serial
  private static final long serialVersionUID = -375031399175696316L;

  @Id
  private String id;

  @Column("file_name")
  private String fileName;

  @Column("start_time")
  private Date startTime;

  @Column("status")
  private String status;
}
