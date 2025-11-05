package com.yme.movementsservice.application.service;

import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Client;
import com.yme.movementsservice.infraestructure.util.ErrorMessages;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
import com.yme.movementsservice.infraestructure.input.adapter.rest.exception.ResourceAlreadyExistsException;
import com.yme.movementsservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.movementsservice.infraestructure.output.adapter.repository.AccountRepository;
import com.yme.movementsservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.movementsservice.application.input.port.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public Account saveAccount(Long clientId, Account account) {
        if (!Objects.isNull(accountRepository.findByAccountNumber(account.getAccountNumber())))
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_ACCOUNT_ALREADY_EXISTS);

        Client client = clientRepository.findByClientId(clientId);
        if (Objects.isNull(client))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND);

        account.setClient(client);
        account.setFinalBalance(account.getInitialBalance());
        return accountRepository.save(account);
    }

    /**
     * Update an account for a client.
     *
     * @param clientId clientId.
     * @param account account data.
     * @return an updated account object.
     */
    @Override
    public Account updateAccount(Long clientId, Account account) {
        Client client = clientRepository.findByClientId(clientId);
        if (Objects.isNull(client))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND);
        return accountRepository.save(account);
    }

    /**
     * Get all existing accounts.
     *
     * @return a list of accounts.
     */
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Get an account by accountNumber.
     *
     * @param accountNumber accountNumber.
     * @return an account.
     */
    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (Objects.isNull(account))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND);
       return account;
    }

    /**
     * Delete an account.
     *
     * @param accountNumber accountNumber.
     * @return a Boolean value.
     */
    @Override
    public Boolean deleteAccountByAccountNumber(String accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        accountRepository.deleteById(account.getId());
        return true;
    }
}
