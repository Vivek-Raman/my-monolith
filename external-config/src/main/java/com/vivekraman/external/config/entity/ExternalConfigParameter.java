package com.vivekraman.external.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Entity(name = "external-config-parameter")
@Table(name = "external-config-parameter")
public class ExternalConfigParameter implements Serializable {
  @Serial
  private static final long serialVersionUID = -567703283814445120L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "created_on")
  @CreatedDate
  private String createdOn;

  @Column(name = "updated_on")
  @LastModifiedDate
  private Date updatedOn;

  @Column(name = "config_key", unique = true, nullable = false)
  private String configKey;

  @Column(name = "config_value")
  private String configValue;
}
