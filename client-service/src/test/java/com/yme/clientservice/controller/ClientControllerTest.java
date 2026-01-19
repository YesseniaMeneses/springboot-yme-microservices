package com.yme.clientservice.controller;

import com.yme.clientservice.application.input.port.ClientService;
import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.input.adapter.rest.impl.ClientController;
import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks private ClientController clientController;
    @Mock private ClientService clientService;

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
        when(clientService.saveClient(any(Client.class))).thenReturn(Mono.just(client));

        ResponseEntity<Mono<Client>> response = clientController.saveClient(client);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        StepVerifier.create(response.getBody())
                .expectNext(client)
                .verifyComplete();

        verify(clientService).saveClient(client);
    }

    @Test
    void updateClient() {
        when(clientService.updateClient(any(Client.class))).thenReturn(Mono.just(client));

        ResponseEntity<Mono<Client>> response = clientController.updateClient(client);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        StepVerifier.create(response.getBody())
                .expectNext(client)
                .verifyComplete();

        verify(clientService).updateClient(client);
    }

    @Test
    void getAllClients() {
        when(clientService.getAllClients()).thenReturn(Mono.just(List.of(client)));

        ResponseEntity<Mono<List<Client>>> response = clientController.getAllClients();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        StepVerifier.create(response.getBody())
                .expectNext(List.of(client))
                .verifyComplete();

        verify(clientService).getAllClients();
    }

    @Test
    void getClientByClientId() {
        when(clientService.getClientByClientId(ID)).thenReturn(Mono.just(client));

        ResponseEntity<Mono<Client>> response = clientController.getClientByClientId(ID);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        StepVerifier.create(response.getBody())
                .expectNext(client)
                .verifyComplete();

        verify(clientService).getClientByClientId(ID);
    }

    @Test
    void deleteClientByClientId() {
        when(clientService.deleteClientByClientId(ID)).thenReturn(Mono.just(Boolean.TRUE));

        ResponseEntity<Mono<Boolean>> response = clientController.deleteClientByClientId(ID);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        verify(clientService).deleteClientByClientId(ID);
    }
}
