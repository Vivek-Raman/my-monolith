package dev.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "start_time")
  private Date startTime;

  @Column(name = "status")
  private String status;
}
