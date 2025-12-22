package com.vv.personal.twm.market.crdb.v1.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "market_metadata")
public class MarketMetaDataEntity {

  @Id
  @Column(name = "ticker")
  private String ticker;

  @Column(name = "metadata")
  private String metadata;

  @Column(name = "date")
  private int date;
}
