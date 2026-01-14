package com.yme.movementsservice.application.service;

import com.yme.movementsservice.application.input.port.AccountService;
import com.yme.movementsservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.movementsservice.infraestructure.output.adapter.repository.AccountRepository;
import com.yme.movementsservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
import com.yme.movementsservice.infraestructure.util.ErrorMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service methods for Account.
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ClientRepository clientRepository;

    /**
     * Save an account for a client.
     *
     * @param clientId clientId.
     * @param account account data.
     * @return an account object.
     */
    @Override
    public Mono<Account> saveAccount(Long clientId, Account account) {
        return Mono.just(clientRepository.findByClientId(clientId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND)))
                .map(client -> {
                    account.setClient(client);
                    account.setFinalBalance(account.getInitialBalance());
                    return account;
                })
                .map(accountRepository::save);
    }

    /**
     * Update an account for a client.
     *
     * @param clientId clientId.
     * @param account account data.
     * @return an updated account object.
     */
    @Override
    public Mono<Account> updateAccount(Long clientId, Account account) {
        return Mono.just(clientRepository.findByClientId(clientId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND)))
                .map(client -> account)
                .map(accountRepository::save);
    }

    /**
     * Get all existing accounts.
     *
     * @return a list of accounts.
     */
    @Override
    public Mono<List<Account>> getAllAccounts() {
        return Mono.just(accountRepository.findAll());
    }

    /**
     * Get an account by accountNumber.
     *
     * @param accountNumber accountNumber.
     * @return an account.
     */
    @Override
    public Mono<Account> getAccountByAccountNumber(String accountNumber) {
        return Mono.just(accountRepository.findByAccountNumber(accountNumber))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND)))
                .map(account -> account);
    }

    /**
     * Delete an account.
     *
     * @param accountNumber accountNumber.
     * @return a Boolean value.
     */
    @Override
    public Mono<Boolean> deleteAccountByAccountNumber(String accountNumber) {
        return getAccountByAccountNumber(accountNumber)
                .map(account -> {
                    accountRepository.deleteById(account.getId());
                    return Boolean.TRUE;
                });
    }
}
