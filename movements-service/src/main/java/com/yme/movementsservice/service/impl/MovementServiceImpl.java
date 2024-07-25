package com.yme.movementsservice.service.impl;

import com.yme.movementsservice.constant.Constant;
import com.yme.movementsservice.constant.ErrorMessages;
import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Movement;
import com.yme.movementsservice.exception.BusinessException;
import com.yme.movementsservice.exception.ResourceNotFoundException;
import com.yme.movementsservice.repository.AccountRepository;
import com.yme.movementsservice.repository.MovementRepository;
import com.yme.movementsservice.service.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.yme.movementsservice.constant.Constant.DEPOSIT_KEY;
import static com.yme.movementsservice.constant.Constant.WITHDRAWAL_KEY;

/**
 * Service methods for Movement.
 */
@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

    private MovementRepository movementRepository;
    private AccountRepository accountRepository;

    /**
     * Save a movement for an account and update final balance of that account.
     *
     * @param accountNumber accountNumber.
     * @param movement movement data.
     * @return a movement object.
     */
    @Override
    public Movement saveMovement(String accountNumber, Movement movement) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (Objects.isNull(account))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND);

        movement.setDate(new Date());
        movement.setAccount(account);
        Character type = movement.getType();
        BigDecimal amountToMove = movement.getAmount();
        BigDecimal finalBalance = account.getFinalBalance();
        boolean allowMovement = false;

        if (allowDeposit(type, amountToMove)){
            allowMovement = true;
            movement.setDescription(Constant.DEPOSIT_MSG.concat(movement.getAmount().toString()));
            movement.setAvailableBalance(finalBalance.add(amountToMove));
        } else if (allowWithdrawal(type, amountToMove)){
            if (insufficientFunds(amountToMove, finalBalance))
                throw new BusinessException(ErrorMessages.ERROR_INSUFFICIENT_FUNDS);
            allowMovement = true;
            movement.setDescription(Constant.WITHDRAWAL_MSG.concat(amountToMove.toString()));
            movement.setAvailableBalance(finalBalance.subtract(amountToMove));
        }
        if (allowMovement) {
            Movement savedMovement = movementRepository.save(movement);
            account.setFinalBalance(savedMovement.getAvailableBalance());
            accountRepository.save(account);
            return savedMovement;
        }
        throw new BusinessException(ErrorMessages.ERROR_MOVEMENT_NOT_ALLOWED);
    }

    /**
     * Get all existing movements.
     *
     * @return a list of movements.
     */
    @Override
    public List<Movement> getAllMovements() {
        return movementRepository.findAll();
    }

    /**
     * Get all movements for an account.
     *
     * @param accountNumber accountNumber.
     * @return a list of movements.
     */
    @Override
    public List<Movement> getMovementsByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (Objects.isNull(account))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND);

        return movementRepository.findByAccount(account);
    }

    /**
     * Delete a movement by id.
     *
     * @param id id.
     * @return a Boolean value.
     */
    @Override
    public Boolean deleteMovementById(Long id) {
        movementRepository.deleteById(id);
        return true;
    }

    /**
     * Validation for deposits.
     *
     * @param type type of movement.
     * @param amountToMove amount to move.
     * @return a boolean value.
     */
    private boolean allowDeposit(Character type, BigDecimal amountToMove){
        return DEPOSIT_KEY.equals(type) && amountToMove.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Validation for withdrawal.
     *
     * @param type type of movement.
     * @param amountToMove amount to move.
     * @return a boolean value.
     */
    private boolean allowWithdrawal(Character type, BigDecimal amountToMove){
        return WITHDRAWAL_KEY.equals(type) && amountToMove.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Validation for insufficient funds.
     *
     * @param amountToMove amount to move.
     * @param finalBalance final balance.
     * @return a boolean value.
     */
    private boolean insufficientFunds(BigDecimal amountToMove, BigDecimal finalBalance) {
        return amountToMove.compareTo(finalBalance) > 0;
    }
}
