package com.assessment.cts.controller;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.database.model.UserCryptoCurrencyTransaction;
import com.assessment.cts.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/trade")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @GetMapping(path = "/transaction-history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserCryptoCurrencyTransaction>> getTransactionHistory(
            @RequestParam(value = "userId") Long userId) {
        return new ResponseEntity<>(tradingService.getUserTradingTransaction(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/user-wallet", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserCryptoCurrency>> getUserWallet(
            @RequestParam(value = "userId") Long userId) {
        return new ResponseEntity<>(tradingService.getUserWallet(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserCryptoCurrency>> depositCrypto(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "cryptoSymbol") String cryptoSymbol,
            @RequestParam(value = "amount") Double amount
    ) {
        return new ResponseEntity<>(tradingService
                .depositToCryptoCurrency(userId, cryptoSymbol, amount), HttpStatus.OK);
    }



}
