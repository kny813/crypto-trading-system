package com.assessment.cts.database.model;

import lombok.*;
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
@Table(name = "user_crypto_currency")
public class UserCryptoCurrency {

    @EmbeddedId
    private UserCryptoCurrencyId userCryptoCurrencyId;

    private BigDecimal balance;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp updatedDateTime;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createdDateTime;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @ToString.Exclude
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "cryptoSymbol", insertable = false, updatable = false)
    @ToString.Exclude
    private CryptoCurrency cryptoCurrency;
}
