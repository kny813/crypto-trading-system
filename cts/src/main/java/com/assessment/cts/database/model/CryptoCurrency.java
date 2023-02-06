package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private String cryptoCode;

    private boolean isActive;

    @CreationTimestamp
    private Timestamp updatedDateTime;

    @CreationTimestamp
    private Timestamp createdDateTime;

}
