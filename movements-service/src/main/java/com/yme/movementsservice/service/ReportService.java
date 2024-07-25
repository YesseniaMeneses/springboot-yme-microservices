package com.yme.movementsservice.service;

import com.yme.movementsservice.domain.MovementsByAccount;

import java.text.ParseException;
import java.util.List;

/**
 * Interface for Report methods.
 */
public interface ReportService {

    /**
     * Get all movements for all accounts of a client.
     *
     * @param dateRange startDate and endDate
     * @param clientId clientId
     * @return a list of movementsByAccount
     * @throws ParseException when date format is incorrect.
     */
    List<MovementsByAccount> getMovementsByAccount(String dateRange, Long clientId) throws ParseException;
}
