package com.vv.personal.twm.market.crdb.v1.data.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Vivek
 * @since 2024-12-08
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "market_transaction")
public class MarketTransactionEntity {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "direction")
  private String direction;

  @Column(name = "ticker")
  private String ticker;

  @Column(name = "qty")
  private double quantity;

  @Column(name = "price")
  private double price;

  @Column(name = "price_per_share")
  private double pricePerShare;

  @Column(name = "date_trade")
  private int tradeDate;

  @Column(name = "date_settlement")
  private int settlementDate;

  @Column(name = "name")
  private String tickerName;

  @Column(name = "sector")
  private String sector;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "cusip")
  private String cusip;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "instrument_type")
  private String instrumentType;

  @Column(name = "country_code")
  private String countryCode;
}
