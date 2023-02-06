package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.UserCryptoCurrencyTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCryptoCurrencyTransactionRepository extends CrudRepository<UserCryptoCurrencyTransaction, Long> {

    List<UserCryptoCurrencyTransaction> findAllByUserIdOrderByCreatedDateTimeDesc(long userId);

}
