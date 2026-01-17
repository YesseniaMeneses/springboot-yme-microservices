package com.yme.movementsservice.application.input.port;

import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface for Account methods.
 */
public interface AccountService {
    /**
     * Save an account.
     *
     * @param clientId
     * @param account
     * @return an account object.
     */
    Mono<Account> saveAccount(Long clientId, Account account);

    /**
     * Update an account.
     *
     * @param clientId
     * @param account
     * @return an updated account.
     */
    Mono<Account> updateAccount(Long clientId, Account account);

    /**
     * Get all existing accounts.
     *
     * @return a list of accounts.
     */
    Mono<List<Account>> getAllAccounts();

    /**
     * Get an account by accountNumber.
     *
     * @param accountNumber
     * @return an account object.
     */
    Mono<Account> getAccountByAccountNumber(String accountNumber);

    /**
     * Delete an account.
     *
     * @param accountNumber
     * @return a Boolean value.
     */
    Mono<Boolean> deleteAccountByAccountNumber(String accountNumber);
}
