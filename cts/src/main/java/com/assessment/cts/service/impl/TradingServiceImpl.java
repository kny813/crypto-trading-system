package com.assessment.cts.service.impl;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.database.model.UserCryptoCurrencyId;
import com.assessment.cts.database.repository.UserCryptoCurrencyRepository;
import com.assessment.cts.database.repository.UserCryptoCurrencyTransactionRepository;
import com.assessment.cts.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class TradingServiceImpl implements TradingService {

    @Autowired
    private UserCryptoCurrencyRepository userCryptoCurrencyRepository;

    @Autowired
    private UserCryptoCurrencyTransactionRepository userCryptoCurrencyTransactionRepository;

    @Override
    public UserCryptoCurrency depositToCryptoCurrency(UserAccount userAccount,
                                                      CryptoCurrency cryptoCurrency,
                                                      BigDecimal amount) {

        UserCryptoCurrencyId userCryptoCurrencyId = new UserCryptoCurrencyId(
                userAccount.getUserId(), cryptoCurrency.getCryptoSymbol());

        UserCryptoCurrency userCryptoCurrency = userCryptoCurrencyRepository
                .findById(userCryptoCurrencyId).orElse(new UserCryptoCurrency());

        if (userCryptoCurrency.getUserCryptoCurrencyId() == null) {
            userCryptoCurrency.setUserCryptoCurrencyId(userCryptoCurrencyId);
            userCryptoCurrency.setBalance(BigDecimal.valueOf(0));
        }

        userCryptoCurrency.setCryptoCurrency(cryptoCurrency);
        userCryptoCurrency.setUserAccount(userAccount);
        userCryptoCurrency.setBalance(
                userCryptoCurrency.getBalance().add(amount));
        userCryptoCurrency.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));

        return userCryptoCurrencyRepository.save(userCryptoCurrency);
    }
}
