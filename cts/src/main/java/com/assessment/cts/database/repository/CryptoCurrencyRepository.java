package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.CryptoCurrency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrency, String> {
}
