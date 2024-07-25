package com.yme.movementsservice.repository;

import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Movement entity.
 */
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccount(Account account);
}
