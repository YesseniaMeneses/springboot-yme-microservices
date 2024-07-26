package com.yme.movementsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.enums.AccountType;
import com.yme.movementsservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Random;

class AccountControllerTest extends BaseTest {

    @Autowired private AccountController accountController;
    @Autowired private ClientRepository clientRepository;
    private Client client;

    @BeforeEach
    public void init() {
        String jsonClient = "{\n" +
                "    \"identification\": \"1766666666\",\n" +
                "    \"clientId\": 6,\n" +
                "    \"name\": \"YESSENIA MENESES\",\n" +
                "    \"address\": \"Vicente Paredes N0-0000 y Francisco Guarderas\",\n" +
                "    \"phoneNumber\": \"0980000000\",\n" +
                "    \"password\": \"00000\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            client = objectMapper.readValue(jsonClient, Client.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Account prepareAccountData(Long clientId, String accountNumber) {
        client.setClientId(clientId);
        client.setIdentification(String.valueOf(new Random().nextInt()));
        Client savedClient = clientRepository.save(client);
        Account account = new Account();
        account.setAccountType(AccountType.AHO);
        account.setAccountNumber(accountNumber);
        account.setClient(savedClient);
        return account;

    }

    @Test
    void saveAccount() {
        Long clientId = new Random().nextLong();
        String accountNumber = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1234567890);
        ResponseEntity<Account> response = accountController.saveAccount(clientId, prepareAccountData(clientId, accountNumber));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertInstanceOf(Account.class, response.getBody());
        Assertions.assertEquals(accountNumber, response.getBody().getAccountNumber());
    }

    @Test
    void updateAccount() {
        Long clientId = new Random().nextLong();
        String accountNumber = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1234567890);
        ResponseEntity<Account> response = accountController.saveAccount(clientId, prepareAccountData(clientId, accountNumber));
        Account savedAccount = response.getBody();
        Assertions.assertNotNull(savedAccount);
        savedAccount.setAccountType(AccountType.COR);

        response = accountController.updateAccount(clientId, savedAccount);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertInstanceOf(Account.class, response.getBody());
        Assertions.assertEquals(AccountType.COR, response.getBody().getAccountType());
    }

    @Test
    void getAllAccounts() {
        ResponseEntity<List<Account>> response = accountController.getAllAccounts();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getAccountByAccountNumber() {
        Long clientId = new Random().nextLong();
        String accountNumber = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1234567890);
        accountController.saveAccount(clientId, prepareAccountData(clientId, accountNumber));
        ResponseEntity<Account> response = accountController.getAccountByAccountNumber(accountNumber);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(accountNumber, response.getBody().getAccountNumber());
    }

    @Test
    void deleteAccountByAccountNumber() {
        Long clientId = new Random().nextLong();
        String accountNumber = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1111111110);
        accountController.saveAccount(clientId, prepareAccountData(clientId, accountNumber));
        ResponseEntity<Boolean> response = accountController.deleteAccountByAccountNumber(accountNumber);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody());
    }
}
