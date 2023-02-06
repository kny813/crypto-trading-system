package com.assessment.cts.controller;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.service.CryptoCurrencyService;
import com.assessment.cts.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crypto-currency")
public class CryptoCurrencyController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CryptoCurrency> getCryptoCurrency(
            @RequestParam(value = "cryptoCode") String cryptoCode) {
        return new ResponseEntity<>(cryptoCurrencyService.getCryptoCurrency(cryptoCode), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CryptoCurrency> saveCryptoCurrency(
            @RequestBody CryptoCurrency cryptoCurrency
    ) throws CustomException {
        return new ResponseEntity<>(cryptoCurrencyService
                .saveCryptoCurrency(cryptoCurrency), HttpStatus.OK);
    }

    @PostMapping(path = "/pair", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> saveCryptoPair(
            @RequestParam(value = "cryptoCode1") String cryptoCode1,
            @RequestParam(value = "cryptoCode2") String cryptoCode2
    ) throws CustomException {
        return new ResponseEntity<>(cryptoCurrencyService
                .saveCryptoPair(cryptoCode1, cryptoCode2), HttpStatus.OK);
    }
}
