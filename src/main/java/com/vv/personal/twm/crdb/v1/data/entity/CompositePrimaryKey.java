package com.vv.personal.twm.crdb.v1.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompositePrimaryKey implements Serializable {

  @Column(name = "date")
  private int date;

  @Column(name = "ticker")
  private String ticker;
}
