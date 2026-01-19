package com.yme.clientservice.service;

import com.yme.clientservice.application.service.ClientServiceImpl;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.clientservice.infraestructure.output.adapter.mapper.ClientMapper;
import com.yme.clientservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientService;

    private static final Long ID = 123L;
    private static ClientEntity clientEntity;
    private static Client client;

    @BeforeEach
    void setUp() {
        clientEntity = ClientEntity.builder().clientId(ID).build();
        client = Client.builder().clientId(ID).build();
    }

    @Test
    void saveClient() {
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(Mono.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        StepVerifier.create(clientService.saveClient(client))
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository).save(clientEntity);
    }

    @Test
    void updateClient() {
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.findByClientId(any())).thenReturn(Mono.just(clientEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(Mono.just(clientEntity));

        StepVerifier.create(clientService.updateClient(client))
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository).findByClientId(ID);
        verify(clientRepository).save(clientEntity);
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(Flux.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        StepVerifier.create(clientService.getAllClients())
                .expectNextMatches(list ->
                        list.size() == 1 &&
                        list.get(0).getClientId().equals(ID))
                .verifyComplete();

        verify(clientRepository).findAll();
    }

    @Test
    void getClientByClientId() {
        when(clientRepository.findByClientId(any())).thenReturn(Mono.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        StepVerifier.create(clientService.getClientByClientId(ID))
                .expectNextMatches(client::equals)
                .verifyComplete();

        verify(clientRepository).findByClientId(ID);
    }

    @Test
    void givenNoClientWhenGetClientByClientIdThenThrowException() {
        when(clientRepository.findByClientId(any())).thenReturn(Mono.empty());

        StepVerifier.create(clientService.getClientByClientId(ID))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void deleteClientByClientId() {
        when(clientRepository.findByClientId(any())).thenReturn(Mono.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.deleteById(clientEntity.getId())).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClientByClientId(ID))
                .expectNext(Boolean.TRUE)
                .verifyComplete();

        verify(clientRepository).deleteById(clientEntity.getId());
    }
}
