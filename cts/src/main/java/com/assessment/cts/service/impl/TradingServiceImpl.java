package com.assessment.cts.service.impl;

import com.assessment.cts.database.model.*;
import com.assessment.cts.database.repository.UserCryptoCurrencyRepository;
import com.assessment.cts.database.repository.UserCryptoCurrencyTransactionRepository;
import com.assessment.cts.service.CryptoCurrencyService;
import com.assessment.cts.service.TradingService;
import com.assessment.cts.service.UserAccountService;
import com.assessment.cts.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TradingServiceImpl implements TradingService {

    @Autowired
    private UserCryptoCurrencyRepository userCryptoCurrencyRepository;

    @Autowired
    private UserCryptoCurrencyTransactionRepository userCryptoCurrencyTransactionRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;


    @Override
    public List<UserCryptoCurrencyTransaction> getUserTradingTransaction(long userId) {
        return userCryptoCurrencyTransactionRepository
                .findAllByUserIdOrderByCreatedDateTimeDesc(userId);
    }

    @Override
    public List<UserCryptoCurrency> getUserWallet(long userId) {
        return userCryptoCurrencyRepository.findAllByUserCryptoCurrencyIdUserId(userId);
    }

    @Override
    public List<UserCryptoCurrency> depositToCryptoCurrency(Long userId,
                                                            String cryptoCode,
                                                            Double amount) {

        UserCryptoCurrencyId userCryptoCurrencyId = new UserCryptoCurrencyId(
                userId, cryptoCode);

        UserAccount userAccount = userAccountService.getUser(userId);
        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrency(cryptoCode);

        UserCryptoCurrency userCryptoCurrency = userCryptoCurrencyRepository
                .findById(userCryptoCurrencyId).orElse(new UserCryptoCurrency());

        if (userCryptoCurrency.getUserCryptoCurrencyId() == null) {
            userCryptoCurrency.setUserCryptoCurrencyId(userCryptoCurrencyId);
            userCryptoCurrency.setBalance(BigDecimal.valueOf(0));
        }

        userCryptoCurrency.setCryptoCurrency(cryptoCurrency);
        userCryptoCurrency.setUserAccount(userAccount);
        userCryptoCurrency.setBalance(
                userCryptoCurrency.getBalance().add(BigDecimal.valueOf(amount)));
        userCryptoCurrency.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        userCryptoCurrencyRepository.save(userCryptoCurrency);

        saveTransaction(userId, null, null, cryptoCode, BigDecimal.valueOf(amount));

        return getUserWallet(userId);
    }

    // TO-DO
    public List<UserCryptoCurrency> sellCryptoCurrency(long userId,
                                                        String fromCryptoCode,
                                                        BigDecimal fromAmount,
                                                        String toCryptoCode) throws CustomException {

        validateCryptoPair(fromCryptoCode, toCryptoCode);

        synchronized (this) {
            UserCryptoCurrency fromCryptoCurrency = userCryptoCurrencyRepository
                    .findById(new UserCryptoCurrencyId(userId, fromCryptoCode)).orElse(null);


            UserCryptoCurrencyId toUserCryptoCurrencyId = new UserCryptoCurrencyId(userId, toCryptoCode);
            UserCryptoCurrency toCryptoCurrency = userCryptoCurrencyRepository
                    .findById(toUserCryptoCurrencyId)
                    .orElse(new UserCryptoCurrency(toUserCryptoCurrencyId, BigDecimal.ZERO));

            if (fromCryptoCurrency == null) {
                throw new CustomException(String.format(
                        "User[$s] does not have source crypto currency [%s].", userId, fromCryptoCode));
            }


        }

        return getUserWallet(userId);
    }

    public void saveTransaction(long userId,
                                String fromCryptoCode,
                                BigDecimal fromAmount,
                                String toCryptoCode,
                                BigDecimal toAmount) {
        userCryptoCurrencyTransactionRepository.save(
                UserCryptoCurrencyTransaction.builder()
                        .userId(userId)
                        .fromCryptoCode(fromCryptoCode)
                        .fromAmount(fromAmount)
                        .toCryptoCode(toCryptoCode)
                        .toAmount(toAmount)
                        .build()
        );
    }

    private void validateCryptoPair(String cryptoCode1, String cryptoCode2)
            throws CustomException{

        CryptoPairId cryptoPairId = new CryptoPairId(cryptoCode1, cryptoCode2);
        if (cryptoCurrencyService.getCryptoPair(cryptoPairId) == null) {
            throw new CustomException("Not a valid pair crypto.");
        }
    }

}
