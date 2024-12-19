package com.yme.clientservice.controller;

import com.yme.clientservice.domain.Client;
import com.yme.clientservice.mapper.ClientMapper;
import com.yme.clientservice.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @InjectMocks private ClientController clientController;
    @Mock private ClientService clientService;
    @Mock private ClientMapper clientMapper;

    private static final Long ID = 123L;

    @Test
    public void saveClient() {
        var client = Client.builder().clientId(ID).build();

        when(clientService.saveClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.saveClient(client);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(123L);
        verify(clientService).saveClient(client);
    }

    @Test
    public void updateClient() {
        var client = Client.builder().clientId(ID).build();
        when(clientService.updateClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.updateClient(client);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(ID);
        verify(clientService).updateClient(client);
    }

    @Test
    public void getAllClients() {
        List<Client> clientEntities = new ArrayList<>();
        when(clientService.getAllClients()).thenReturn(clientEntities);

        ResponseEntity<List<Client>> response = clientController.getAllClients();
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(clientService).getAllClients();
    }

    @Test
    public void getClientByClientId() {
        var client = Client.builder().clientId(ID).build();
        when(clientService.getClientByClientId(any())).thenReturn(client);

        ResponseEntity<Client> response = clientController.getClientByClientId(ID);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(ID);
        verify(clientService).getClientByClientId(ID);
    }

    @Test
    public void deleteClientByClientId() {
        clientController.deleteClientByClientId(ID);
        verify(clientService).deleteClientByClientId(ID);
    }
}
