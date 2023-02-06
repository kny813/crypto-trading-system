package com.assessment.cts.database.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp updatedDateTime;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createdDateTime;

    @ManyToOne
    @JoinColumn(name = "cryptoCode1", insertable = false, updatable = false)
    @ToString.Exclude
    private CryptoCurrency cryptoCurrency1;

    @ManyToOne
    @JoinColumn(name = "cryptoCode2", insertable = false, updatable = false)
    @ToString.Exclude
    private CryptoCurrency cryptoCurrency2;
}
