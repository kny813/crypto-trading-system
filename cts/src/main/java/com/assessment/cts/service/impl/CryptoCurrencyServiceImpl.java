package com.assessment.cts.service.impl;

import com.assessment.cts.constant.CryptoCurrencyConstant;
import com.assessment.cts.database.dto.BinancePriceDto;
import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.repository.CryptoCurrencyRepository;
import com.assessment.cts.service.CryptoCurrencyService;

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

    @Override
    public void updateCryptoCurrencyFromOnline() {
        logger.info("Schedule Task: updateCryptoCurrencyFromOnline");

        RestTemplate restTemplate = new RestTemplate();
        BinancePriceDto[] binancePriceDtos = restTemplate.getForObject(
                CryptoCurrencyConstant.BINANCE_TICKET_URL, BinancePriceDto[].class);
        Map<String, List<LinkedHashMap>> map = restTemplate.getForObject(
                CryptoCurrencyConstant.HOUBI_TICKET_URL, HashMap.class);

        Map<String, BinancePriceDto> binancePriceDtoMap = IntStream.range(0, binancePriceDtos.length).boxed()
                .collect(Collectors.toMap(
                        i -> binancePriceDtos[i].getSymbol().toUpperCase(), i -> binancePriceDtos[i]));

        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();

        for(LinkedHashMap houbiPriceMap: map.get(CryptoCurrencyConstant.HOUBI_ROOT_KEY)) {

            String houbiSymbol = ((String) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_SYMBOL_KEY)).toUpperCase();
            BigDecimal houbiAsk = BigDecimal.valueOf(
                    (Double) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_ASK_PRICE_KEY));
            BigDecimal houbiBid = BigDecimal.valueOf(
                    (Double) houbiPriceMap.get(CryptoCurrencyConstant.HOUBI_BID_PRICE_KEY));

            CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findById(houbiSymbol)
                    .orElse(new CryptoCurrency());

            if (binancePriceDtoMap.get(houbiSymbol) == null) {
                cryptoCurrencyList.add(convertDto(cryptoCurrency, houbiSymbol,houbiAsk, houbiBid));
            } else {
                cryptoCurrencyList.add(
                        convertDto(cryptoCurrency,
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

        cryptoCurrencyList.addAll(
                binancePriceDtoMap
                        .values()
                        .parallelStream()
                        .map(this::convertDtoFromBinance)
                        .collect(Collectors.toList()));


        cryptoCurrencyRepository.saveAll(cryptoCurrencyList);
        logger.info("Saved " + cryptoCurrencyList.size() + " crypto currencies into db.");
    }

    private CryptoCurrency convertDto(CryptoCurrency cryptoCurrency, String symbol, BigDecimal askPrice, BigDecimal bidPrice) {
        cryptoCurrency.setCryptoSymbol(symbol);
        cryptoCurrency.setAskPrice(askPrice);
        cryptoCurrency.setBidPrice(bidPrice);
        cryptoCurrency.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        return cryptoCurrency;
    }

    private CryptoCurrency convertDtoFromBinance(BinancePriceDto binancePriceDto) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository
                .findById(binancePriceDto.getSymbol().toUpperCase())
                .orElse(new CryptoCurrency());

        cryptoCurrency.setCryptoSymbol(binancePriceDto.getSymbol().toUpperCase());
        cryptoCurrency.setBidPrice(binancePriceDto.getBidPrice());
        cryptoCurrency.setAskPrice(binancePriceDto.getAskPrice());
        cryptoCurrency.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));

        return cryptoCurrency;
    }
}
