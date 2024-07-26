package com.yme.movementsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.constant.ErrorMessages;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.enums.AccountType;
import com.yme.movementsservice.exception.ResourceAlreadyExistsException;
import com.yme.movementsservice.exception.ResourceNotFoundException;
import com.yme.movementsservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());
    }

    @Transactional
    @Test
    void updateAccount() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());
        savedAccount.setAccountType(AccountType.COR);
        Account updatedAccount = accountService.updateAccount(insertedClient.getClientId(), savedAccount);
        Assertions.assertEquals(AccountType.COR, updatedAccount.getAccountType());
    }

    @Transactional
    @Test
    void getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        Assertions.assertNotNull(accounts);
    }

    @Transactional
    @Test
    void deleteAccountByAccountNumber() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());

        Boolean deletedAccount = accountService.deleteAccountByAccountNumber(savedAccount.getAccountNumber());
        Assertions.assertTrue(deletedAccount);
    }

    @Transactional
    @Test
    void getAccountByAccountNumber() {
        Client insertedClient = clientRepository.save(client);
        Assertions.assertNotNull(insertedClient);
        Assertions.assertEquals(CLIENT_ID, insertedClient.getClientId());

        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());

        Account foundAccount = accountService.getAccountByAccountNumber(ACCOUNT_NUMBER);
        Assertions.assertNotNull(foundAccount);
    }

    @Transactional
    @Test
    void givenExistingAccountWhenSaveSameAccountShouldThrowERROR_ACCOUNT_ALREADY_EXISTS() {
        Client insertedClient = clientRepository.save(client);
        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());

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

        Account savedAccount = accountService.saveAccount(insertedClient.getClientId(), account);
        Assertions.assertNotNull(savedAccount);
        Assertions.assertEquals(ACCOUNT_NUMBER, savedAccount.getAccountNumber());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                accountService.getAccountByAccountNumber(ACCOUNT_NUMBER_2));
        Assertions.assertEquals(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND, exception.getMessage());
    }
}
