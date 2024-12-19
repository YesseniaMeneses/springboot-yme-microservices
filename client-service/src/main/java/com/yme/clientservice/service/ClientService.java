package com.yme.clientservice.service;



import com.yme.clientservice.domain.Client;
import com.yme.clientservice.entity.ClientEntity;

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
    Client saveClient(Client client);

    /**
     * Update a client.
     *
     * @param client
     * @return an updated client.
     */
    Client updateClient(Client client);

    /**
     * Get all clients.
     *
     * @return a list of clients.
     */
    List<Client> getAllClients();

    /**
     * Search a client by clientId.
     *
     * @param clientId
     * @return a client.
     */
    Client getClientByClientId(Long clientId);

    /**
     * Delete a client.
     *
     * @param clientId
     * @return Boolean value.
     */
    Boolean deleteClientByClientId(Long clientId);
}
