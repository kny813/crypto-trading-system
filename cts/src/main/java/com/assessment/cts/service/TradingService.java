package com.assessment.cts.service;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.model.UserCryptoCurrency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TradingService {

    UserCryptoCurrency depositToCryptoCurrency(UserAccount userAccount,
                                               CryptoCurrency cryptoCurrency,
                                               BigDecimal amount);

}
