package com.assessment.cts.service.impl;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.repository.CryptoCurrencyRepository;
import com.assessment.cts.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Override
    public CryptoCurrency saveCryptoCurrency(CryptoCurrency cryptoCurrency) {
        return cryptoCurrencyRepository.save(cryptoCurrency);
    }

    @Override
    public CryptoCurrency getCryptoCurrency(String cryptoSymbol) {
        return cryptoCurrencyRepository.findById(cryptoSymbol).orElse(null);
    }

    @Override
    public List<CryptoCurrency> getCryptoCurrencyList() {
        return (List<CryptoCurrency>) cryptoCurrencyRepository.findAll();
    }
}
