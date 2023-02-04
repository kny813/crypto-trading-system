package com.assessment.cts.service.impl;

import com.assessment.cts.database.model.UserAccount;
import com.assessment.cts.database.repository.UserRepository;
import com.assessment.cts.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAccount saveUser(UserAccount userAccount) {
        return userRepository.save(userAccount);
    }

    @Override
    public UserAccount getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<UserAccount> getUserList() {
        return (List<UserAccount>) userRepository.findAll();
    }
}
