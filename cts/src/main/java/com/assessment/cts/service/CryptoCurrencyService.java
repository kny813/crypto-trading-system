package com.assessment.cts.service;

import com.assessment.cts.database.model.CryptoCurrency;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CryptoCurrencyService {

    CryptoCurrency saveCryptoCurrency(CryptoCurrency cryptoCurrency);

    CryptoCurrency getCryptoCurrency(String cryptoSymbol);

    List<CryptoCurrency> getCryptoCurrencyList();
}
