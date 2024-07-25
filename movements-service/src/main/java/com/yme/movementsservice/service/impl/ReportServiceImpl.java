package com.yme.movementsservice.service.impl;

import com.yme.movementsservice.constant.Constant;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Client;
import com.yme.movementsservice.entity.Movement;
import com.yme.movementsservice.domain.AccountDetail;
import com.yme.movementsservice.domain.Filter;
import com.yme.movementsservice.domain.MovementDetail;
import com.yme.movementsservice.domain.MovementsByAccount;
import com.yme.movementsservice.repository.AccountRepository;
import com.yme.movementsservice.repository.ClientRepository;
import com.yme.movementsservice.repository.MovementRepository;
import com.yme.movementsservice.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service methods for Report.
 */
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private MovementRepository movementRepository;
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;

    /**
     * Get all movements grouped by all accounts of a client.
     *
     * @param dateRange startDate and endDate
     * @param clientId clientId
     * @return
     */
    @Override
    public List<MovementsByAccount> getMovementsByAccount(String dateRange, Long clientId) {
        Filter filter = mapToFilter(dateRange, clientId);
        List<String> accountNumbers = getAccountsByClient(filter.getClientId());

        if (!accountNumbers.isEmpty()){
            Date startDate = com.yme.movementsservice.validation.Date.stringToDate(filter.getStartDate());
            Date endDate = com.yme.movementsservice.validation.Date.stringToDate(filter.getEndDate());
            List<Movement> movements = getAllMovements();

            Map<Account, List<Movement>> map = movements
                    .stream()
                    .filter(m -> accountNumbers.contains(m.getAccountNumber()))
                    .toList()
                    .stream()
                    .filter(m -> (m.getDate().after(startDate) || m.getDate().equals(startDate))
                            && (m.getDate().before(endDate) || m.getDate().equals(endDate)))
                    .toList()
                    .stream()
                    .collect(Collectors.groupingBy(m ->  getAccount(m.getAccountNumber())));

            return map.entrySet()
                    .stream()
                    .map(m -> mapToMovementsByAccount(m.getKey(), m.getValue()))
                    .toList();
        }
        return Collections.emptyList();
    }

    /**
     * Get all existing movements.
     *
     * @return a list of movements.
     */
    private List<Movement> getAllMovements() {
        List<Movement> movements = movementRepository.findAll();
        for(Movement m: movements) {
            m.setAccountNumber(m.getAccount().getAccountNumber());
        }
        return movements;
    }

    /**
     * Get account data for an accountNumber.
     *
     * @param accountNumber accountNumber.
     * @return an account object.
     */
    private Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    /**
     * Get all account numbers for a specific client.
     *
     * @param clientId clientId.
     * @return a list of string.
     */
    private List<String> getAccountsByClient(Long clientId) {
        return accountRepository
                .findByClient(clientRepository.findByClientId(clientId))
                .stream()
                .map(Account::getAccountNumber)
                .toList();
    }

    /**
     * Map account data to a report object.
     *
     * @param account account.
     * @return account data for report.
     */
    private AccountDetail mapToAccountDetail(Account account) {
        Client client = clientRepository.findByClientId(account.getClient().getClientId());
        AccountDetail accountDetailReport = new AccountDetail();
        accountDetailReport.setClientName(client.getName());
        accountDetailReport.setAccountNumber(account.getAccountNumber());
        accountDetailReport.setAccountType(account.getAccountType());
        accountDetailReport.setStatus(account.getStatus());
        accountDetailReport.setInitialBalance(account.getInitialBalance());
        accountDetailReport.setFinalBalance(account.getFinalBalance());
        return accountDetailReport;
    }

    /**
     * Map movement data to a report object.
     *
     * @param movement movement.
     * @return movement data for report.
     */
    private MovementDetail mapToMovementDetail(Movement movement) {
        String date = com.yme.movementsservice.validation.Date.dateToString(movement.getDate());
        MovementDetail movementDetail = new MovementDetail();
        movementDetail.setDate(date);
        movementDetail.setType(evaluateMovementType(movement.getType()));
        movementDetail.setMovement(movement.getAmount());
        movementDetail.setAvailableBalance(movement.getAvailableBalance());
        return movementDetail;
    }

    /**
     * Map data to a final report object.
     *
     * @param account account data.
     * @param movements list of movements.
     * @return list of movements by account.
     */
    private MovementsByAccount mapToMovementsByAccount(Account account, List<Movement> movements){
        MovementsByAccount ma = new MovementsByAccount();
        ma.setAccountDetail(mapToAccountDetail(account));
        ma.setMovements(movements.stream().map(this::mapToMovementDetail).toList());
        return ma;
    }

    /**
     * Map filter data to a filter object.
     *
     * @param dateRange startDate and endDate.
     * @param clientId clientId.
     * @return a filter object.
     */
    private Filter mapToFilter(String dateRange, Long clientId) {
        String[] range = com.yme.movementsservice.validation.Date.validateRangeDate(dateRange);
        Filter filter = new Filter();
        filter.setClientId(clientId);
        filter.setStartDate(range[0]);
        filter.setEndDate(range[1]);
        return filter;
    }

    /**
     * Validate allowed movement types.
     *
     * @param type movement type.
     * @return a key movement.
     */
    private String evaluateMovementType(Character type) {
        return Constant.DEPOSIT_KEY.equals(type)? Constant.DEPOSIT_VALUE: Constant.WITHDRAWAL_VALUE;
    }
}
