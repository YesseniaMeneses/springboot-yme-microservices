package com.yme.clientservice.application.input.port;


import com.yme.clientservice.domain.Client;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface for Client methods.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param client
     * @return a client.
     */
    Mono<Client> saveClient(Client client);

    /**
     * Update a client.
     *
     * @param client
     * @return an updated client.
     */
    Mono<Client> updateClient(Client client);

    /**
     * Get all clients.
     *
     * @return a list of clients.
     */
    Mono<List<Client>> getAllClients();

    /**
     * Search a client by clientId.
     *
     * @param clientId
     * @return a client.
     */
    Mono<Client> getClientByClientId(Long clientId);

    /**
     * Delete a client.
     *
     * @param clientId
     * @return Boolean value.
     */
    Mono<Boolean> deleteClientByClientId(Long clientId);
}
