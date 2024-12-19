package com.yme.clientservice.service.impl;

import com.yme.clientservice.constant.ErrorMessages;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.entity.ClientEntity;
import com.yme.clientservice.exception.ResourceAlreadyExistsException;
import com.yme.clientservice.exception.ResourceNotFoundException;
import com.yme.clientservice.mapper.ClientMapper;
import com.yme.clientservice.repository.ClientRepository;
import com.yme.clientservice.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service methods for Client.
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    /**
     * Save a client.
     *
     * @param client
     * @return a client.
     */
    @Override
    public Client saveClient(Client client) {
        if (!Objects.isNull(clientRepository.findByClientId(client.getClientId())))
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_CLIENT_ALREADY_EXISTS);
        return clientMapper.toClient(clientRepository.save(clientMapper.toClientEntity(client)));
    }

    /**
     * Update a client.
     *
     * @param client
     * @return an updated client.
     */
    @Override
    public Client updateClient(Client client) {
        return clientMapper.toClient(clientRepository.save(clientMapper.toClientEntity(client)));
    }

    /**
     * Get all clients.
     *
     * @return a list of clients.
     */
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll().stream().map(clientMapper::toClient).toList();
    }

    /**
     * Search a client.
     *
     * @param clientId
     * @return a client.
     */
    @Override
    public Client getClientByClientId(Long clientId) {
        ClientEntity clientEntity = clientRepository.findByClientId(clientId);
        if (Objects.isNull(clientEntity))
            throw new ResourceNotFoundException(ErrorMessages.ERROR_CLIENT_NOT_FOUND);
        return clientMapper.toClient(clientEntity);
    }

    /**
     * Delete a client.
     *
     * @param clientId
     * @return a Boolean value.
     */
    @Override
    public Boolean deleteClientByClientId(Long clientId) {
        Client clientEntity = getClientByClientId(clientId);
        clientRepository.deleteById(clientEntity.getClientId());
        return true;
    }
}
