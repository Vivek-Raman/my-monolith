package dev.vivekraman.inventory.history.analysis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @JoinColumn(name = "analysis_job_fk")
  @ManyToOne(targetEntity = AnalysisJob.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JsonBackReference
  private AnalysisJob analysisJob;

  @Column(name = "inventory_id")
  private String inventoryIdentifier;

  @Column(name = "transaction_primary_key")
  private String transactionPrimaryKey;

  @Column(name = "warehouse_merchant_code")
  private String warehouseMerchantCode;

  @Column(name = "warehouse_item_sku")
  private String warehouseItemSku;

  @Column(name = "warehouse_code")
  private String warehouseCode;

  @Column(name = "original_stock")
  private Integer originalStock;

  @Column(name = "available_stock")
  private Integer availableStock;

  @Column(name = "available_stock_transaction_quantity")
  private Integer availableStockTransactionQuantity;

  @Column(name = "original_stock_transaction_quantity")
  private Integer originalStockTransactionQuantity;

  @Column(name = "tracking_id")
  private String trackingId;

  @Column(name = "originator")
  private String originator;

  @Column(name = "unique_id")
  private String uniqueId;

  @Column(name = "safety_stock")
  private Integer safetyStock;

  @Column(name = "normal_transaction_fk")
  private String normalTransactionFk;

  @Column(name = "transaction_quantity")
  private Integer transactionQuantity;

  @Column(name = "customer_logon_id")
  private String customerLogonId;

  @Column(name = "transaction_description")
  private String transactionDescription;

  @Column(name = "username")
  private String username;

  @Column(name = "transaction_date")
  private String transactionDate;

  @Column(name = "order_item_id")
  private String orderItemId;

  @Column(name = "action_key")
  private String actionKey;

  @Column(name = "action_type")
  private String actionType;

  @Column(name = "used_api")
  private String usedApi;

  @Column(name = "request_id")
  private String requestId;

  @Column(name = "channel_id")
  private String channelId;

  @Column(name = "client_id")
  private String clientId;
}
