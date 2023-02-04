package com.assessment.cts.database.repository;

import com.assessment.cts.database.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, Long> {
}
