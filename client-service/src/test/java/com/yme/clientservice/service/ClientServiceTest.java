package com.yme.clientservice.service;

import com.yme.clientservice.application.service.ClientServiceImpl;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.output.adapter.mapper.ClientMapper;
import com.yme.clientservice.infraestructure.output.adapter.repository.ClientRepository;
import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ClientServiceImpl.class})
public class ClientServiceTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
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
    public void saveClient() {
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);

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
    }
}
