package com.yme.movementsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.application.input.port.AccountService;
import com.yme.movementsservice.domain.enums.AccountType;
import com.yme.movementsservice.infraestructure.input.adapter.rest.exception.ResourceAlreadyExistsException;
import com.yme.movementsservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.movementsservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Client;
import com.yme.movementsservice.infraestructure.util.ErrorMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertThrows;

class AccountServiceTest extends BaseTest {

    @Autowired private AccountService accountService;
    @Autowired private ClientRepository clientRepository;
    private Account account;
    private Client client;

    private static final Long CLIENT_ID = 3L;
    private static final String ACCOUNT_NUMBER = "2233333333";

    @BeforeEach
    void init() {
        String jsonAccount = "{\n" +
                "    \"accountNumber\": \"2233333333\",\n" +
                "    \"accountType\": \"AHO\"\n" +
                "}";

        String jsonClient = "{\n" +
                "    \"identification\": \"1733333333\",\n" +
                "    \"clientId\": 3,\n" +
                "    \"name\": \"YESSENIA MENESES\",\n" +
                "    \"address\": \"Vicente Paredes N0-0000 y Francisco Guarderas\",\n" +
                "    \"phoneNumber\": \"0980000000\",\n" +
                "    \"password\": \"00000\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            account = objectMapper.readValue(jsonAccount, Account.class);
            client = objectMapper.readValue(jsonClient, Client.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    void saveAccount() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));
    }

    @Transactional
    @Test
    void updateAccount() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));

        Mono<Account> updatedAccount = savedAccount.flatMap(acc -> {
            acc.setAccountType(AccountType.COR);
            return accountService.updateAccount(insertedClient.getClientId(), acc);
        });
        Assertions.assertEquals(AccountType.COR, updatedAccount.map(Account::getAccountType));
    }

    @Transactional
    @Test
    void getAllAccounts() {
        Mono<List<Account>> accounts = accountService.getAllAccounts();
        Assertions.assertNotNull(accounts);
    }

    @Transactional
    @Test
    void deleteAccountByAccountNumber() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));

        Mono<Boolean> deletedAccount = accountService.deleteAccountByAccountNumber(savedAccount.map(Account::getAccountNumber).block());
        Assertions.assertTrue(deletedAccount.block());
    }

    @Transactional
    @Test
    void getAccountByAccountNumber() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));

        Mono<Account> foundAccount = accountService.getAccountByAccountNumber(ACCOUNT_NUMBER);
        Assertions.assertNotNull(foundAccount);
    }

    @Transactional
    @Test
    void givenExistingAccountWhenSaveSameAccountShouldThrowERROR_ACCOUNT_ALREADY_EXISTS() {
        Client insertedClient = clientRepository.save(client);
        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () ->
                accountService.saveAccount(insertedClient.getClientId(), account));
        Assertions.assertEquals(ErrorMessages.ERROR_ACCOUNT_ALREADY_EXISTS, exception.getMessage());
    }

    @Transactional
    @Test
    void givenNoClientWhenSaveAccountShouldThrowERROR_CLIENT_NOT_FOUND() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                accountService.saveAccount(0L, account));
        Assertions.assertEquals(ErrorMessages.ERROR_CLIENT_NOT_FOUND, exception.getMessage());
    }

    @Transactional
    @Test
    void givenNoClientWhenUpdateAccountShouldThrowERROR_CLIENT_NOT_FOUND() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                accountService.updateAccount(0L, account));
        Assertions.assertEquals(ErrorMessages.ERROR_CLIENT_NOT_FOUND, exception.getMessage());
    }

    @Transactional
    @Test
    void givenNotSavedAccountWhenGetAccountByAccountNumberShouldThrowERROR_ACCOUNT_NOT_FOUND() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Mono<Account> savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.map(Account::getAccountNumber));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                accountService.getAccountByAccountNumber(ACCOUNT_NUMBER_2));
        Assertions.assertEquals(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND, exception.getMessage());
    }
}
