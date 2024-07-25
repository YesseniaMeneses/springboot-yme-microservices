package com.yme.movementsservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class AccountRepositoryTest extends BaseTest {

    @Autowired private AccountRepository accountRepository;
    @Autowired private ClientRepository clientRepository;
    private Account account;
    private Client client;

    @BeforeEach
    public void init() {
        String jsonAccount = "{\n" +
                "    \"accountNumber\": \"2200000000\",\n" +
                "    \"accountType\": \"AHO\"\n" +
                "}";

        String jsonClient = "{\n" +
                "    \"identification\": \"1700000000\",\n" +
                "    \"clientId\": 1,\n" +
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
    void insertAccount() {
        Client clientInserted = clientRepository.save(client);
        account.setClient(clientInserted);
        accountRepository.save(account);
        Account accountInserted = accountRepository.findByAccountNumber("2200000000");
        Assertions.assertNotNull(accountInserted);
        Assertions.assertEquals(1L, accountInserted.getClient().getClientId());
    }
}
