package com.vivekraman.inventory.history.analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseInventoryHistoryTransaction implements Serializable {
  @Serial
  private static final long serialVersionUID = -7122852452059825913L;

  private String warehouseMerchantCode;
  private String warehouseItemSku;
  private String warehouseCode;
  private Double originalStock;
  private Double availableStock;
  private Double transactionQuantity;
  private String customerLogonId;
  private String transactionDescription;
  private String username;
  private String transactionDate;
  private String orderItemId;
  private String actionKey;
  private String usedApi;
  private String requestId;
  private String channelId;
  private String clientId;

}
