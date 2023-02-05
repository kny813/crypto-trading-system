package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "crypto_currency")
public class CryptoCurrency {

    @Id
    private String cryptoSymbol;

    @Column(columnDefinition = "numeric(19,10)")
    private BigDecimal bidPrice;

    @Column(columnDefinition = "numeric(19,10)")
    private BigDecimal askPrice;

    @CreationTimestamp
    private Timestamp updatedDateTime;

    @CreationTimestamp
    private Timestamp createdDateTime;

}
