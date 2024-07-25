package com.yme.clientservice.service;

import com.yme.clientservice.entity.Client;
import com.yme.clientservice.repository.ClientRepository;
import com.yme.clientservice.service.impl.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @InjectMocks private ClientServiceImpl clientService;
    @Mock private ClientRepository clientRepository;

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(client);
        assertThat(savedClient.getClientId()).isSameAs(client.getClientId());
        verify(clientRepository).save(client);
    }

    @Test
    public void updateClient() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(client);
        assertThat(savedClient.getClientId()).isSameAs(client.getClientId());
        verify(clientRepository).save(client);

        String name = "YESSENIA MENESES";
        savedClient.setName(name);
        Client updatedClient = clientService.updateClient(savedClient);
        assertThat(updatedClient.getName()).isSameAs(name);
    }

    @Test
    public void getAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        when(clientRepository.findAll()).thenReturn(clients);
        List<Client> allClients = clientService.getAllClients();
        assertThat(allClients.size()).isEqualTo(1);
        verify(clientRepository).findAll();
    }

    @Test
    public void getClientByClientId() {
        Client client = new Client();
        client.setClientId(123L);
        when(clientRepository.findByClientId(any())).thenReturn(client);

        Client foundClient = clientService.getClientByClientId(123L);
        assertThat(foundClient.getClientId()).isSameAs(client.getClientId());
        verify(clientRepository).findByClientId(123L);
    }

    @Test
    public void deleteClientByClientId() {
        clientRepository.deleteById(123L);
        verify(clientRepository).deleteById(123L);
    }
}
