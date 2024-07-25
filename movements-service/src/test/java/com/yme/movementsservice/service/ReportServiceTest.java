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
import com.yme.movementsservice.domain.MovementDetail;
import com.yme.movementsservice.domain.MovementsByAccount;
import com.yme.movementsservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertThrows;

class ReportServiceTest extends BaseTest {

    @Autowired private ReportService reportService;
    @Autowired private MovementService movementService;
    @Autowired private AccountService accountService;
    @Autowired private ClientRepository clientRepository;
    private Client client;

    private static final Long CLIENT_ID = 5L;
    private static final String ACCOUNT_NUMBER_1 = "2255555555";
    private static final String ACCOUNT_NUMBER_2 = "2266666666";
    private static final String ACCOUNT_NUMBER_3 = "2277777777";
    private static final String DATE_RANGE = "20240101,20241231";

    @BeforeEach
    void init() {
        String jsonClient = "{\n" +
                "    \"identification\": \"1755555555\",\n" +
                "    \"clientId\": 5,\n" +
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

    private void saveAccount(String accountNumber) {
        clientRepository.save(client);
        Account account = new Account();
        account.setAccountType(AHO);
        account.setAccountNumber(accountNumber);
        Account savedAccount = accountService.saveAccount(CLIENT_ID, account);
        Assertions.assertNotNull(savedAccount);
    }

    private void saveMovement(String account, Character type, BigDecimal amount) {
        Movement movement = new Movement();
        movement.setType(type);
        movement.setAmount(amount);
        movementService.saveMovement(account, movement);
    }

    @Transactional
    @Test
    void getMovementsByAccount() throws ParseException {
        saveAccount( ACCOUNT_NUMBER_1);
        saveAccount(ACCOUNT_NUMBER_2);
        saveAccount(ACCOUNT_NUMBER_3);

        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        Assertions.assertEquals(4, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_1).size());

        saveMovement(ACCOUNT_NUMBER_2, Constant.DEPOSIT_KEY, BigDecimal.valueOf(50.00));
        saveMovement(ACCOUNT_NUMBER_2, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(20.50));
        Assertions.assertEquals(2, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_2).size());

        saveMovement(ACCOUNT_NUMBER_3, Constant.DEPOSIT_KEY, BigDecimal.valueOf(500.99));
        saveMovement(ACCOUNT_NUMBER_3, Constant.DEPOSIT_KEY, BigDecimal.valueOf(500));
        saveMovement(ACCOUNT_NUMBER_3, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(1000.99));
        Assertions.assertEquals(3, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_3).size());

        List<MovementsByAccount> report = reportService.getMovementsByAccount(DATE_RANGE, CLIENT_ID);
        Assertions.assertEquals(3, report.size());

        List<List<MovementDetail>> movementsAccount1 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_1.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(4, movementsAccount1.get(0).size());

        List<List<MovementDetail>> movementsAccount2 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_2.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(2, movementsAccount2.get(0).size());

        List<List<MovementDetail>> movementsAccount3 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_3.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(3, movementsAccount3.get(0).size());
    }

    @Transactional
    @Test
    void givenNoMovementsWhenGetMovementsByAccountShouldReturnEmptyList() throws ParseException {
        saveAccount(ACCOUNT_NUMBER_1);
        saveAccount(ACCOUNT_NUMBER_2);
        saveAccount(ACCOUNT_NUMBER_3);

        List<MovementsByAccount> report = reportService.getMovementsByAccount(DATE_RANGE, CLIENT_ID);
        Assertions.assertEquals(0, report.size());
    }

    @Transactional
    @Test
    void givenNoMovementsForACCOUNT_NUMBER_2WhenGetMovementsByAccountShouldNotReturnACCOUNT_NUMBER_2() throws ParseException {
        saveAccount(ACCOUNT_NUMBER_1);
        saveAccount(ACCOUNT_NUMBER_2);
        saveAccount(ACCOUNT_NUMBER_3);

        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        Assertions.assertEquals(4, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_1).size());

        saveMovement(ACCOUNT_NUMBER_3, Constant.DEPOSIT_KEY, BigDecimal.valueOf(500.99));
        saveMovement(ACCOUNT_NUMBER_3, Constant.DEPOSIT_KEY, BigDecimal.valueOf(500));
        saveMovement(ACCOUNT_NUMBER_3, Constant.WITHDRAWAL_KEY, BigDecimal.valueOf(1000.99));
        Assertions.assertEquals(3, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_3).size());

        List<MovementsByAccount> report = reportService.getMovementsByAccount(DATE_RANGE, CLIENT_ID);
        Assertions.assertEquals(2, report.size());

        List<List<MovementDetail>> movementsAccount4 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_1.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(4, movementsAccount4.get(0).size());

        List<List<MovementDetail>> movementsAccount5 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_2.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(0, movementsAccount5.size());

        List<List<MovementDetail>> movementsAccount6 = report.stream()
                .filter(r -> ACCOUNT_NUMBER_3.equals(r.getAccountDetail().getAccountNumber()))
                .map(MovementsByAccount::getMovements)
                .toList();
        Assertions.assertEquals(3, movementsAccount6.get(0).size());

    }

    @Transactional
    @Test
    void givenIncorrectDateWhenGetMovementsByAccountShouldThrowErrorERROR_DATE_FORMAT() {
        saveAccount(ACCOUNT_NUMBER_1);

        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        saveMovement(ACCOUNT_NUMBER_1, Constant.DEPOSIT_KEY, BigDecimal.valueOf(100.00));
        Assertions.assertEquals(2, movementService.getMovementsByAccountNumber(ACCOUNT_NUMBER_1).size());

        String dateRange = "20240101-20241231";
        BusinessException exception = assertThrows(BusinessException.class, () ->
                reportService.getMovementsByAccount(dateRange, CLIENT_ID));
        Assertions.assertEquals(ErrorMessages.ERROR_DATE_RANGE_FORMAT.concat("yyyyMMdd,yyyyMMdd"), exception.getMessage());
    }
}
