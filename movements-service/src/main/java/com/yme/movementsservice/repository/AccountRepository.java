package com.yme.movementsservice.repository;

import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Account entity.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);
    List<Account> findByClient(Client client);
}
