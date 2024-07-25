package com.yme.movementsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.constant.Constant;
import com.yme.movementsservice.constant.ErrorMessages;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.entity.Movement;
import com.yme.movementsservice.exception.BusinessException;
import com.yme.movementsservice.exception.ResourceNotFoundException;
import com.yme.movementsservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertThrows;

class MovementServiceTest extends BaseTest {

    @Autowired private MovementService movementService;
    @Autowired private AccountService accountService;
    @Autowired private ClientRepository clientRepository;
    private Account account;
    private Client client;

    private static final Long CLIENT_ID = 4L;
    private static final String ACCOUNT_NUMBER = "2244444444";

    @BeforeEach
    void init() {
        String jsonAccount = "{\n" +
                "    \"accountNumber\": \"2244444444\",\n" +
                "    \"accountType\": \"AHO\"\n" +
                "}";

        String jsonClient = "{\n" +
                "    \"identification\": \"1744444444\",\n" +
                "    \"clientId\": 4,\n" +
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

    private void saveAccount() {
        clientRepository.save(client);
        Account savedAccount = accountService.saveAccount(CLIENT_ID, account);
        Assertions.assertNotNull(savedAccount);
    }

    private Movement saveMovement(String account, Character type, BigDecimal amount) {
        Movement movement = new Movement();
        movement.setType(type);
        movement.setAmount(amount);
        Movement savedMovement = movementService.saveMovement(account, movement);
        Assertions.assertNotNull(movement);
        return savedMovement;
    }

    @Transactional
    @Test
    void saveMovement() {
        saveAccount();
        Movement movement = saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        Assertions.assertEquals(0, BigDecimal.valueOf(100.00).compareTo(movement.getAvailableBalance()));

        movement = saveMovement(ACCOUNT_NUMBER, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(40.57));
        Assertions.assertEquals(BigDecimal.valueOf(59.43), movement.getAvailableBalance());

        movement = saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(500.29));
        Assertions.assertEquals(BigDecimal.valueOf(559.72), movement.getAvailableBalance());

        Account account = accountService.getAccountByAccountNumber(ACCOUNT_NUMBER);
        Assertions.assertEquals(BigDecimal.valueOf(559.72), account.getFinalBalance());

        movement = saveMovement(ACCOUNT_NUMBER, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(559.72));
        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(movement.getAvailableBalance()));

        account = accountService.getAccountByAccountNumber(ACCOUNT_NUMBER);
        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(account.getFinalBalance()));
    }

    @Transactional
    @Test
    void getAllMovements() {
        saveAccount();
        saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        List<Movement> movements = movementService.getAllMovements();
        Assertions.assertNotNull(movements);
    }

    @Transactional
    @Test
    void getMovementsByAccountNumber() {
        saveAccount();
        saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        List<Movement> movements = movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER);
        Assertions.assertEquals(1, movements.size());
    }

    @Transactional
    @Test
    void deleteMovementById() {
        saveAccount();
        Movement savedMovement = saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        Boolean deletedMovement = movementService.deleteMovementById(savedMovement.getId());
        Assertions.assertTrue(deletedMovement);
    }

    @Transactional
    @Test
    void givenNoAccountSavedWhenSaveMovementShouldThrowErrorERROR_ACCOUNT_NOT_FOUND() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100)));
        Assertions.assertEquals(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND, exception.getMessage());
    }

    @Transactional
    @Test
    void givenIncorrectDepositDataWhenSaveMovementShouldThrowErrorERROR_MOVEMENT_NOT_ALLOWED() {
        saveAccount();
        BusinessException exception = assertThrows(BusinessException.class, () ->
                saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.ZERO));
        Assertions.assertEquals(ErrorMessages.ERROR_MOVEMENT_NOT_ALLOWED, exception.getMessage());
    }

    @Transactional
    @Test
    void givenIncorrectWithdrawalDataWhenSaveMovementShouldThrowErrorERROR_MOVEMENT_NOT_ALLOWED() {
        saveAccount();
        BusinessException exception = assertThrows(BusinessException.class, () ->
                saveMovement(ACCOUNT_NUMBER, 'X', BigDecimal.valueOf(100)));
        Assertions.assertEquals(ErrorMessages.ERROR_MOVEMENT_NOT_ALLOWED, exception.getMessage());
    }

    @Transactional
    @Test
    void givenIncorrectAmountForWithdrawalWhenSaveMovementShouldThrowErrorERROR_MOVEMENT_NOT_ALLOWED() {
        saveAccount();
        BusinessException exception = assertThrows(BusinessException.class, () ->
                saveMovement(ACCOUNT_NUMBER, Constant.WITHDRAWAL_KEY, BigDecimal.ZERO));
        Assertions.assertEquals(ErrorMessages.ERROR_MOVEMENT_NOT_ALLOWED, exception.getMessage());
    }

    @Transactional
    @Test
    void givenInsufficientFundsWhenSaveMovementShouldThrowErrorERROR_INSUFFICIENT_FUNDS() {
        saveAccount();
        Movement savedMovement = saveMovement(ACCOUNT_NUMBER, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100));
        Assertions.assertEquals(BigDecimal.valueOf(100), savedMovement.getAmount());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                saveMovement(ACCOUNT_NUMBER, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(150)));
        Assertions.assertEquals(ErrorMessages.ERROR_INSUFFICIENT_FUNDS, exception.getMessage());
    }
}
