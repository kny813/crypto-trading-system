package com.assessment.cts.service;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.database.model.UserCryptoCurrencyTransaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface TradingService {

    List<UserCryptoCurrency> depositToCryptoCurrency(Long userId,
                                                     String cryptoSymbol,
                                                     Double amount);

    List<UserCryptoCurrencyTransaction> getUserTradingTransaction(long userId);

    List<UserCryptoCurrency> getUserWallet(long userId);

}
