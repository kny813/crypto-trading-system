package com.assessment.cts.controller;

import com.assessment.cts.database.model.CryptoCurrency;
import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.service.UserAccountService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAccount> getUser(
            @RequestParam(value = "userId") Long userId) {
        return new ResponseEntity<>(userAccountService.getUser(userId), HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAccount> saveUser(
            @RequestBody UserAccount userAccount) {
        return new ResponseEntity<>(userAccountService.saveUser(userAccount), HttpStatus.OK);
    }


}
