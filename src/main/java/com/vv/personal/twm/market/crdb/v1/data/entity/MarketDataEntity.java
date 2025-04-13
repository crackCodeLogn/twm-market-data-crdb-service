package com.vv.personal.twm.market.crdb.v1.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "market_data")
public class MarketDataEntity {

  @EmbeddedId private CompositePrimaryKey id;

  @Column(name = "price")
  private double price;
}
