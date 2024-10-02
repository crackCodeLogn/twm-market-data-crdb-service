package com.vv.personal.twm.crdb.v1.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Data
@Embeddable
public class CompositePrimaryKey implements Serializable {

    @Column(name = "date")
    private int date;

    @Column(name = "ticker")
    private String ticker;
}
