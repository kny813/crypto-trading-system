package com.assessment.cts.database.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "crypto_pair")
public class CryptoPair {

    @EmbeddedId
    private CryptoPairId cryptoPairId;

    private Timestamp updatedDateTime;

    private Timestamp createdDateTime;

    @ManyToOne
    @JoinColumn(name = "cryptoSymbol1", insertable = false, updatable = false)
    @ToString.Exclude
    private CryptoCurrency cryptoCurrency1;

    @ManyToOne
    @JoinColumn(name = "cryptoSymbol2", insertable = false, updatable = false)
    @ToString.Exclude
    private CryptoCurrency cryptoCurrency2;
}
