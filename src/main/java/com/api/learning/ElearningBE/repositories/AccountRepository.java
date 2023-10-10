package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Boolean existsAccountByEmail(String email);
    Account findByEmail(String email);
}
