package com.yme.movementsservice.service;

import com.yme.movementsservice.entity.Movement;

import java.util.List;

/**
 * Interface for Movement methods.
 */
public interface MovementService {

    /**
     * Save a movement.
     *
     * @param accountNumber
     * @param movement
     * @return a movement.
     */
    Movement saveMovement(String accountNumber, Movement movement);

    /**
     * Get all existing movements.
     *
     * @return a list of movements.
     */
    List<Movement> getAllMovements();

    /**
     * Get all movements for an account.
     *
     * @param accountNumber
     * @return a list of movements.
     */
    List<Movement> getMovementsByAccountNumber(String accountNumber);

    /**
     * Delete a movement.
     * @param id
     * @return a Boolean value.
     */
    Boolean deleteMovementById(Long id);
}
