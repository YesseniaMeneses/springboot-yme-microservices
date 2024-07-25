package com.yme.movementsservice.service;

import com.yme.movementsservice.entity.Account;

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
    Account saveAccount(Long clientId, Account account);

    /**
     * Update an account.
     *
     * @param clientId
     * @param account
     * @return an updated account.
     */
    Account updateAccount(Long clientId, Account account);

    /**
     * Get all existing accounts.
     *
     * @return a list of accounts.
     */
    List<Account> getAllAccounts();

    /**
     * Get an account by accountNumber.
     *
     * @param accountNumber
     * @return an account object.
     */
    Account getAccountByAccountNumber(String accountNumber);

    /**
     * Delete an account.
     *
     * @param accountNumber
     * @return a Boolean value.
     */
    Boolean deleteAccountByAccountNumber(String accountNumber);
}
