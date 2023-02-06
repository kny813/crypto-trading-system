package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.UserCryptoCurrency;
import com.assessment.cts.database.model.UserCryptoCurrencyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCryptoCurrencyRepository extends CrudRepository<UserCryptoCurrency, UserCryptoCurrencyId> {

    List<UserCryptoCurrency> findAllByUserCryptoCurrencyIdUserId(Long userId);

}
