package com.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "warehouse_inventory_history_transaction")
public class WarehouseInventoryHistoryTransaction implements Serializable {
  @Serial
  private static final long serialVersionUID = -7122852452059825913L;

  @Id
  @GeneratedValue
  private String id;

  @Column("warehouse_merchant_code")
  private String warehouseMerchantCode;

  @Column("warehouse_item_sku")
  private String warehouseItemSku;

  @Column("warehouse_code")
  private String warehouseCode;

  @Column("original_stock")
  private Double originalStock;

  @Column("available_stock")
  private Double availableStock;

  @Column("available_stock_transaction_quantity")
  private Integer availableStockTransactionQuantity;

  @Column("original_stock_transaction_quantity")
  private Integer originalStockTransactionQuantity;

  @Column("tracking_id")
  private String trackingId;

  @Column("originator")
  private String originator;

  @Column("unique_id")
  private String uniqueId;

  @Column("safety_stock")
  private Long safetyStock;

  @Column("normal_transaction_fk")
  private String normalTransactionFk;

  @Column("transaction_quantity")
  private Double transactionQuantity;

  @Column("customer_logon_id")
  private String customerLogonId;

  @Column("transaction_description")
  private String transactionDescription;

  @Column("username")
  private String username;

  @Column("transaction_date")
  private String transactionDate;

  @Column("order_item_id")
  private String orderItemId;

  @Column("action_key")
  private String actionKey;

  @Column("action_type")
  private String actionType;

  @Column("used_api")
  private String usedApi;

  @Column("request_id")
  private String requestId;

  @Column("channel_id")
  private String channelId;

  @Column("client_id")
  private String clientId;
}
