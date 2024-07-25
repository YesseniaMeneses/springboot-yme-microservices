package com.yme.movementsservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.entity.Movement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class MovementRepositoryTest extends BaseTest {

    @Autowired private MovementRepository movementRepository;
    @Autowired private AccountRepository accountRepository;
    @Autowired private ClientRepository clientRepository;

    private Movement movement;
    private Account account;
    private Client client;

    @BeforeEach
    void init() {
        String jsonMovement = "{\n" +
                "    \"type\": \"D\",\n" +
                "    \"amount\": 100\n" +
                "}";

        String jsonAccount = "{\n" +
                "    \"accountNumber\": \"2211111111\",\n" +
                "    \"accountType\": \"AHO\"\n" +
                "}";

        String jsonClient = "{\n" +
                "    \"identification\": \"1711111111\",\n" +
                "    \"clientId\": 2,\n" +
                "    \"name\": \"YESSENIA MENESES\",\n" +
                "    \"address\": \"Vicente Paredes N0-0000 y Francisco Guarderas\",\n" +
                "    \"phoneNumber\": \"0980000000\",\n" +
                "    \"password\": \"00000\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            movement = objectMapper.readValue(jsonMovement, Movement.class);
            account = objectMapper.readValue(jsonAccount, Account.class);
            client = objectMapper.readValue(jsonClient, Client.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    Account insertAccount() {
        Client savedClient = clientRepository.save(client);
        account.setClient(savedClient);
        Account savedAccount = accountRepository.save(account);
        Assertions.assertNotNull(savedAccount);
        return savedAccount;
    }

    @Transactional
    @Test
    void insertMovement() {
        Account savedAccount = insertAccount();
        movement.setAccount(savedAccount);
        Movement savedMovement = movementRepository.save(movement);
        Assertions.assertNotNull(savedMovement);
        List<Movement> movements = movementRepository.findByAccount(savedAccount);
        Assertions.assertEquals(1, movements.size());
    }
}
