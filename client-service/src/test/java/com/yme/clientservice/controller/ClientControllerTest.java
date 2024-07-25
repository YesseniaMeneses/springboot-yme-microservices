package com.yme.clientservice.controller;

import com.yme.clientservice.entity.Client;
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

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientService.saveClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.saveClient(client);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(123L);
        verify(clientService).saveClient(client);
    }

    @Test
    public void updateClient() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientService.updateClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.updateClient(client);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(123L);
        verify(clientService).updateClient(client);
    }

    @Test
    public void getAllClients() {
        List<Client> clients = new ArrayList<>();
        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getAllClients();
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(clientService).getAllClients();
    }

    @Test
    public void getClientByClientId() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientService.getClientByClientId(any())).thenReturn(client);

        ResponseEntity<Client> response = clientController.getClientByClientId(123L);
        assertThat(response.getStatusCode()).isSameAs(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getClientId()).isEqualTo(123L);
        verify(clientService).getClientByClientId(123L);
    }

    @Test
    public void deleteClientByClientId() {
        clientController.deleteClientByClientId(123L);
        verify(clientService).deleteClientByClientId(123L);
    }
}
