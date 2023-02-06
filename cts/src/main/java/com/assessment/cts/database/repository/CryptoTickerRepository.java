package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.CryptoTicker;
import org.springframework.data.repository.CrudRepository;

public interface CryptoTickerRepository extends CrudRepository<CryptoTicker, String> {
}
