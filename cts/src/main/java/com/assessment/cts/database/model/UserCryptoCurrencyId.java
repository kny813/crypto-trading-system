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
public class UserCryptoCurrencyId implements Serializable {

    private long userId;
    private String cryptoSymbol;

}
