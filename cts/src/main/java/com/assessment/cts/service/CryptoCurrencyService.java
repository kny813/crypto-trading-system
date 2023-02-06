package com.assessment.cts.service;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.CryptoPair;
import com.assessment.cts.database.model.CryptoPairId;
import com.assessment.cts.util.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CryptoCurrencyService {

    CryptoCurrency saveCryptoCurrency(CryptoCurrency cryptoCurrency);

    CryptoCurrency getCryptoCurrency(String cryptoCode);

    List<CryptoCurrency> getCryptoCurrencyList();

    void updateCryptoTickerFromOnline();

    List<String> saveCryptoPair(String cryptoCode1, String cryptoCode2) throws CustomException;

    CryptoPair getCryptoPair(CryptoPairId cryptoPairId);
}
