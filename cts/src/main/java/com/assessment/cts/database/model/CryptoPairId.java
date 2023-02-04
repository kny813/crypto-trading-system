package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CryptoPairId implements Serializable {
    private String cryptoSymbol1;
    private String cryptoSymbol2;
}
