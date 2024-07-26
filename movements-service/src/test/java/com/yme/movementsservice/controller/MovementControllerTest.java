package com.yme.movementsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.constant.Constant;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.entity.Movement;
import com.yme.movementsservice.enums.AccountType;
import com.yme.movementsservice.repository.ClientRepository;
import com.yme.movementsservice.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

class MovementControllerTest extends BaseTest {

    @Autowired private MovementController movementController;
    @Autowired private AccountService accountService;
    @Autowired private ClientRepository clientRepository;
    private Client client;

    @BeforeEach
    public void init() {
        String jsonClient = "{\n" +
                "    \"identification\": \"1777777777\",\n" +
                "    \"clientId\": 7,\n" +
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

    private String createAccount() {
        Long clientId = new Random().nextLong();
        String accountNumber = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1234567890);
        String identification = String.valueOf(new Random().nextInt());
        client.setClientId(clientId);
        client.setIdentification(identification);
        Client savedClient = clientRepository.save(client);

        Account account = new Account();
        account.setAccountType(AccountType.AHO);
        account.setAccountNumber(accountNumber);
        account.setClient(savedClient);
        accountService.saveAccount(clientId, account);
        return accountNumber;
    }

    private Movement prepareMovementData(Character type, BigDecimal amount) {
        Movement movement = new Movement();
        movement.setType(type);
        movement.setAmount(amount);
        return movement;
    }

    @Test
    void saveMovement() {
        String accountNumber = createAccount();
        Movement movement = prepareMovementData(Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        ResponseEntity<Movement> response = movementController.saveMovement(accountNumber, movement);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertInstanceOf(Movement.class, response.getBody());
    }

    @Test
    void getAllMovements() {
        ResponseEntity<List<Movement>> response = movementController.getAllMovements();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void getMovementsByAccountNumber() {
        String accountNumber = createAccount();
        Movement movement = prepareMovementData(Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        movementController.saveMovement(accountNumber, movement);
        movement = prepareMovementData(Constant.DEPOSIT_KEY, BigDecimal.valueOf(50));
        movementController.saveMovement(accountNumber, movement);

        ResponseEntity<List<Movement>> response = movementController.getMovementsByAccountNumber(accountNumber);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void deleteMovementById() {
        String accountNumber = createAccount();
        Movement movement = prepareMovementData(Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        ResponseEntity<Movement> response = movementController.saveMovement(accountNumber, movement);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertInstanceOf(Movement.class, response.getBody());

        ResponseEntity<Boolean> responseDelete = movementController.deleteMovementById(response.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        Assertions.assertNotNull(responseDelete.getBody());
        Assertions.assertTrue(responseDelete.getBody());
    }
}
