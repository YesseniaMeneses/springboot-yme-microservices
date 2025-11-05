package com.yme.movementsservice.infraestructure.output.adapter.repository;

import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Movement entity.
 */
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccount(Account account);
}
