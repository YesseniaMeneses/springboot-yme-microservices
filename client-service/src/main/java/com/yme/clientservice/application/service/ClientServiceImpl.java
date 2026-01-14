package com.yme.clientservice.application.service;

import com.yme.clientservice.application.input.port.ClientService;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.clientservice.infraestructure.output.adapter.mapper.ClientMapper;
import com.yme.clientservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.clientservice.infraestructure.util.ErrorMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service methods for Client.
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Save a client.
     *
     * @param client
     * @return a client.
     */
    @Override
    public Mono<Client> saveClient(Client client) {
        return clientRepository.save(clientMapper.toClientEntity(client))
                .map(clientMapper::toClient);
    }

    /**
     * Update a client.
     *
     * @param client
     * @return an updated client.
     */
    @Override
    public Mono<Client> updateClient(Client client) {
        return getClientByClientId(client.getClientId())
                .map(clientMapper::toClientEntity)
                .flatMap(clientRepository::save)
                .map(clientMapper::toClient);
    }

    /**
     * Get all clients.
     *
     * @return a list of clients.
     */
    @Override
    public Mono<List<Client>> getAllClients() {
        return clientRepository.findAll()
                .map(clientMapper::toClient)
                .collectList();
    }

    /**
     * Search a client.
     *
     * @param clientId
     * @return a client.
     */
    @Override
    public Mono<Client> getClientByClientId(Long clientId) {
        return clientRepository.findByClientId(clientId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND)))
                .map(clientMapper::toClient);
    }

    /**
     * Delete a client.
     *
     * @param clientId
     * @return a Boolean value.
     */
    @Override
    public Mono<Boolean> deleteClientByClientId(Long clientId) {
        return getClientByClientId(clientId)
                .map(clientMapper::toClientEntity)
                .map(clientEntity -> clientRepository.deleteById(clientEntity.getId()))
                .map(a -> Boolean.TRUE);
    }
}
