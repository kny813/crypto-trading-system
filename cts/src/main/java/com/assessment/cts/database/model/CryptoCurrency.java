package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "crypto_currency")
public class CryptoCurrency {

    @Id
    private String cryptoSymbol;

    private BigDecimal bidPrice;

    private BigDecimal askPrice;

    private Timestamp updatedDateTime;

    private Timestamp createdDateTime;

}
