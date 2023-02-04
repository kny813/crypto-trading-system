package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    private BigDecimal bidPrice;

    private BigDecimal askPrice;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp updatedDateTime;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createdDateTime;

}
