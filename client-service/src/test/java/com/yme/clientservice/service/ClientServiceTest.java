package com.yme.clientservice.service;

import com.yme.clientservice.application.service.ClientServiceImpl;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.input.adapter.rest.exception.ResourceNotFoundException;
import com.yme.clientservice.infraestructure.output.adapter.mapper.ClientMapper;
import com.yme.clientservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
<<<<<<< HEAD
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
>>>>>>> origin/feature/HEXAGONAL
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

<<<<<<< HEAD
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
=======
>>>>>>> origin/feature/HEXAGONAL
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

<<<<<<< HEAD
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ClientServiceImpl.class})
public class ClientServiceTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
=======
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
>>>>>>> origin/feature/HEXAGONAL
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
<<<<<<< HEAD
    public void saveClient() {
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
=======
    void saveClient() {
>>>>>>> origin/feature/HEXAGONAL
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(Mono.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        StepVerifier.create(clientService.saveClient(client))
                .expectNext(client)
                .verifyComplete();

<<<<<<< HEAD
        StepVerifier.create(clientService.saveClient(client))
                .expectNextMatches(client::equals)
                .verifyComplete();
    }

    @Test
    public void updateClient() {
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.findByClientId(any())).thenReturn(Mono.just(clientEntity));

        StepVerifier.create(clientService.updateClient(client))
                .expectNextMatches(client::equals)
                .verifyComplete();
    }

    //@Test
    public void getAllClients() {
        var clientEntities = new ClientEntity();

        when(clientRepository.findAll()).thenReturn(Flux.just(clientEntities));

        Mono<List<Client>> clientList = clientService.getAllClients();
        assertThat(clientList.map(List::size)).isEqualTo(1);
        verify(clientRepository).findAll();
    }

    //@Test
    public void getClientByClientId() {
        var clientEntity = ClientEntity.builder().clientId(ID).build();
        var client = Client.builder().clientId(ID).status(false).build();

        when(clientRepository.findByClientId(any())).thenReturn(Mono.just(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        Mono<Client> foundClientEntity = clientService.getClientByClientId(ID);
        assertThat(foundClientEntity.map(Client::getClientId)).isSameAs(clientEntity.getClientId());
        verify(clientRepository).findByClientId(ID);
    }

    //@Test
    public void deleteClientByClientId() {
        clientRepository.deleteById(ID);
        verify(clientRepository).deleteById(ID);
=======
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
>>>>>>> origin/feature/HEXAGONAL
    }
}
