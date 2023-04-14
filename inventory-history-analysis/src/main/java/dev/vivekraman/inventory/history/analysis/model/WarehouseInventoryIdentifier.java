package dev.vivekraman.inventory.history.analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseInventoryIdentifier implements Serializable {
  @Serial
  private static final long serialVersionUID = 2108926218442881061L;

  private String warehouseItemSku;
  private String warehouseMerchantCode;
  private String warehouseCode;

  public String generateIdentifier() {
    return String.join("--", warehouseItemSku, warehouseMerchantCode, warehouseCode);
  }
}
