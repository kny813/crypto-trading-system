package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.CryptoPair;
import com.assessment.cts.database.model.CryptoPairId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPairRepository extends CrudRepository<CryptoPair, CryptoPairId> {
}
