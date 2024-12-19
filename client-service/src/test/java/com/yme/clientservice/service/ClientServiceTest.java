package com.yme.clientservice.service;

import com.yme.clientservice.domain.Client;
import com.yme.clientservice.entity.ClientEntity;
import com.yme.clientservice.mapper.ClientMapper;
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
    @Mock private ClientMapper clientMapper;

    private static final Long ID = 123L;

    @Test
    public void saveClient() {
        var clientEntity = ClientEntity.builder().clientId(ID).build();
        var client = Client.builder().clientId(ID).build();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);

        Client savedClientEntity = clientService.saveClient(client);
        assertThat(savedClientEntity.getClientId()).isSameAs(clientEntity.getClientId());
        verify(clientRepository).save(clientEntity);
    }

    @Test
    public void updateClient() {
        var clientEntity = ClientEntity.builder().clientId(ID).status(false).build();
        var client = Client.builder().clientId(ID).status(false).build();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);

        Client updatedClientEntity = clientService.updateClient(client);
        assertThat(updatedClientEntity.getStatus()).isFalse();
    }

    @Test
    public void getAllClients() {
        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(new ClientEntity());

        when(clientRepository.findAll()).thenReturn(clientEntities);

        List<Client> clientList = clientService.getAllClients();
        assertThat(clientList.size()).isEqualTo(1);
        verify(clientRepository).findAll();
    }

    @Test
    public void getClientByClientId() {
        var clientEntity = ClientEntity.builder().clientId(ID).build();
        var client = Client.builder().clientId(ID).status(false).build();

        when(clientRepository.findByClientId(any())).thenReturn(clientEntity);
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(client);

        Client foundClientEntity = clientService.getClientByClientId(ID);
        assertThat(foundClientEntity.getClientId()).isSameAs(clientEntity.getClientId());
        verify(clientRepository).findByClientId(ID);
    }

    @Test
    public void deleteClientByClientId() {
        clientRepository.deleteById(ID);
        verify(clientRepository).deleteById(ID);
    }
}
