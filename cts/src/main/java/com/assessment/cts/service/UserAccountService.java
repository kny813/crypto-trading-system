package com.assessment.cts.service;

import com.assessment.cts.database.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAccountService {

    UserAccount saveUser(UserAccount userAccount);

    UserAccount getUser(Long userId);

    List<UserAccount> getUserList();
}
