package com.assessment.cts.service.impl;

import com.assessment.cts.constant.CryptoCurrencyConstant;
import com.assessment.cts.database.dto.BinancePriceDto;
import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.CryptoPair;
import com.assessment.cts.database.model.CryptoPairId;
import com.assessment.cts.database.model.CryptoTicker;
import com.assessment.cts.database.repository.CryptoCurrencyRepository;
import com.assessment.cts.database.repository.CryptoPairRepository;
import com.assessment.cts.database.repository.CryptoTickerRepository;
import com.assessment.cts.service.CryptoCurrencyService;

import com.assessment.cts.util.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {


    private static Logger logger = LoggerFactory.getLogger(CryptoCurrencyServiceImpl.class);

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    private CryptoPairRepository cryptoPairRepository;

    @Autowired
    private CryptoTickerRepository cryptoTickerRepository;


    @Override
    public CryptoCurrency getCryptoCurrency(String cryptoCode) {
        return cryptoCurrencyRepository.findById(cryptoCode).orElse(null);
    }

    @Override
    public CryptoCurrency saveCryptoCurrency(CryptoCurrency cryptoCurrency) {
        CryptoCurrency cryptoCurrencySave = cryptoCurrencyRepository
                .findById(cryptoCurrency.getCryptoCode()).orElse(cryptoCurrency);
        cryptoCurrencySave.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        cryptoCurrencySave.setActive(cryptoCurrency.isActive());
        return cryptoCurrencyRepository.save(cryptoCurrency);
    }

    @Override
    public List<CryptoCurrency> getCryptoCurrencyList() {
        return (List<CryptoCurrency>) cryptoCurrencyRepository.findAll();
    }

    @Override
    public void updateCryptoTickerFromOnline() {
        logger.info("Schedule Task: updateCryptoCurrencyFromOnline");

        RestTemplate restTemplate = new RestTemplate();
        BinancePriceDto[] binancePriceDtos = restTemplate.getForObject(
                CryptoCurrencyConstant.BINANCE_TICKET_URL, BinancePriceDto[].class);
        Map<String, List<LinkedHashMap>> map = restTemplate.getForObject(
                CryptoCurrencyConstant.HOUBI_TICKET_URL, HashMap.class);

        Map<String, BinancePriceDto> binancePriceDtoMap = IntStream.range(0, binancePriceDtos.length).boxed()
                .collect(Collectors.toMap(
                        i -> binancePriceDtos[i].getSymbol().toUpperCase(), i -> binancePriceDtos[i]));

        List<CryptoTicker> cryptoTickerList = new ArrayList<>();

        for(LinkedHashMap houbiPriceMap: map.get(CryptoCurrencyConstant.HOUBI_ROOT_KEY)) {

            String houbiSymbol = ((String) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_SYMBOL_KEY)).toUpperCase();
            BigDecimal houbiAsk = BigDecimal.valueOf(
                    (Double) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_ASK_PRICE_KEY));
            BigDecimal houbiBid = BigDecimal.valueOf(
                    (Double) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_BID_PRICE_KEY));

            CryptoTicker cryptoTicker = cryptoTickerRepository.findById(houbiSymbol)
                    .orElse(new CryptoTicker());

            if (binancePriceDtoMap.get(houbiSymbol) == null) {
                cryptoTickerList.add(convertDto(cryptoTicker, houbiSymbol,houbiAsk, houbiBid));
            } else {
                cryptoTickerList.add(
                        convertDto(cryptoTicker,
                                    houbiSymbol,
                                    binancePriceDtoMap.get(houbiSymbol)
                                            .getBidPrice()
                                            .max(houbiBid),
                                    binancePriceDtoMap.get(houbiSymbol)
                                            .getAskPrice()
                                            .min(houbiAsk)));

                binancePriceDtoMap.remove(houbiSymbol);
            }
        }

        cryptoTickerList.addAll(
                binancePriceDtoMap
                        .values()
                        .parallelStream()
                        .map(this::convertDtoFromBinance)
                        .collect(Collectors.toList()));


        cryptoTickerRepository.saveAll(cryptoTickerList);
        logger.info("Saved " + cryptoTickerList.size() + " crypto currencies into db.");
    }

    @Override
    public CryptoPair getCryptoPair(CryptoPairId cryptoPairId) {
        return cryptoPairRepository.findById(cryptoPairId).orElse(null);
    }

    @Override
    public List<String> saveCryptoPair(String cryptoCode1, String cryptoCode2) throws CustomException {

        CryptoCurrency cryptoCurrency1 = cryptoCurrencyRepository.findById(cryptoCode1).orElse(null);
        CryptoCurrency cryptoCurrency2 = cryptoCurrencyRepository.findById(cryptoCode2).orElse(null);

        if (cryptoCurrency1 != null && cryptoCurrency2 != null) {
            cryptoPairRepository.save(convertCryptoPairDto(cryptoCode1, cryptoCode2));
        } else {
            throw new CustomException("Crypto currency not found.");
        }

        return cryptoPairRepository.findCryptoPairIdCryptoCode2ByCryptoPairIdCryptoCode1(cryptoCode1);
    }


    private CryptoTicker convertDto(CryptoTicker cryptoTicker, String symbol, BigDecimal askPrice, BigDecimal bidPrice) {
        cryptoTicker.setSymbol(symbol);
        cryptoTicker.setAskPrice(askPrice);
        cryptoTicker.setBidPrice(bidPrice);
        cryptoTicker.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        return cryptoTicker;
    }

    private CryptoTicker convertDtoFromBinance(BinancePriceDto binancePriceDto) {
        CryptoTicker cryptoTicker = cryptoTickerRepository
                .findById(binancePriceDto.getSymbol().toUpperCase())
                .orElse(new CryptoTicker());

        cryptoTicker.setSymbol(binancePriceDto.getSymbol().toUpperCase());
        cryptoTicker.setBidPrice(binancePriceDto.getBidPrice());
        cryptoTicker.setAskPrice(binancePriceDto.getAskPrice());
        cryptoTicker.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));

        return cryptoTicker;
    }

    private CryptoPair convertCryptoPairDto(String cryptoSymbol1, String cryptoSymbol2) {
        CryptoPairId cryptoPairId = new CryptoPairId(cryptoSymbol1, cryptoSymbol2);

        CryptoPair cryptoPair = new CryptoPair();
        cryptoPair.setCryptoPairId(cryptoPairId);
        cryptoPair.setCryptoCurrency1(cryptoCurrencyRepository.findById(cryptoSymbol1).get());
        cryptoPair.setCryptoCurrency2(cryptoCurrencyRepository.findById(cryptoSymbol2).get());
        cryptoPair.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));

        return cryptoPair;
    }
}
