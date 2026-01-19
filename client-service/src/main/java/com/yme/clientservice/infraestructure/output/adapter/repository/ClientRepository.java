package com.yme.clientservice.infraestructure.output.adapter.repository;

import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repository for Client entity.
 */
@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, Long> {

    Mono<ClientEntity> findByClientId(Long clientId);
}
