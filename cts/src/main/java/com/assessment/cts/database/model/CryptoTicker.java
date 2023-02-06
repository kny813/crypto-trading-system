package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "crypto_ticker")
public class CryptoTicker {

    @Id
    private String symbol;

    @Column(columnDefinition = "numeric(19,10)")
    private BigDecimal bidPrice;

    @Column(columnDefinition = "numeric(19,10)")
    private BigDecimal askPrice;

    @CreationTimestamp
    private Timestamp updatedDateTime;

    @CreationTimestamp
    private Timestamp createdDateTime;

}
