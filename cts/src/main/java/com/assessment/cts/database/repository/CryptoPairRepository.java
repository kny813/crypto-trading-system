package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.CryptoPair;
import com.assessment.cts.database.model.CryptoPairId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoPairRepository extends CrudRepository<CryptoPair, CryptoPairId> {

    @Query("SELECT cp.cryptoPairId.cryptoCode2 FROM CryptoPair cp where cp.cryptoPairId.cryptoCode1 = (?1) ")
    List<String> findCryptoPairIdCryptoCode2ByCryptoPairIdCryptoCode1(String cryptoCode1);

}
