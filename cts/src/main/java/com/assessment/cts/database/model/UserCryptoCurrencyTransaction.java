package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_crypto_currency_transaction")
public class UserCryptoCurrencyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userCryptoCurrencyTransactionId;

    private long userId;

    private String fromCryptoSymbol;

    private BigDecimal fromAmount;

    private String toCryptoSymbol;

    private BigDecimal toAmount;

    private Timestamp createdDateTime;
}
