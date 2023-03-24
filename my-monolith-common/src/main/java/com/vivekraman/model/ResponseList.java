package com.vivekraman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ResponseList<T extends Serializable> extends Response<List<T>> implements Serializable {
  private Integer page;
  private Integer size;
  private Long total;

  public ResponseList(Page<T> data) {
    super(data.getContent());
    this.page = data.getNumber();
    this.size = data.getSize();
    this.total = data.getTotalElements();
  }
}
