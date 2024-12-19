package com.yme.clientservice.repository;

import com.yme.clientservice.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Client entity.
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByClientId(Long clientId);
}
