package com.yme.clientservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yme.clientservice.BaseTest;
import com.yme.clientservice.entity.Client;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.junit.Assert.assertThrows;

public class ClientRepositoryTest extends BaseTest {

    @Autowired ClientRepository clientRepository;
    static Client client;

    @BeforeAll
    public static void init() {
        String jsonData = "{\n" +
                "    \"identification\": \"1723209999\",\n" +
                "    \"clientId\": 123,\n" +
                "    \"name\": \"YESSENIA MENESES\",\n" +
                "    \"address\": \"Vicente Paredes N3-154 y Francisco Guarderas\",\n" +
                "    \"phoneNumber\": \"0980000000\",\n" +
                "    \"password\": \"00000\",\n" +
                "    \"gender\": \"F\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            client = objectMapper.readValue(jsonData, Client.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void insertClient() {
        Long clientId = new Random().nextLong();
        String identification = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1237777777);
        client.setClientId(clientId);
        client.setIdentification(identification);
        client.setAge(31);
        clientRepository.save(client);
        Client clientInserted = clientRepository.findByClientId(clientId);
        Assertions.assertNotNull(clientInserted);
    }

    @Transactional
    @Test
    public void givenNoAgeWheninsertClientShouldThrowConstraintViolationException() {
        Long clientId = new Random().nextLong();
        String identification = String.valueOf(new Random().nextInt(Integer.SIZE - 1) + 1237777777);
        client.setClientId(clientId);
        client.setIdentification(identification);
        client.setAge(null);

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                clientRepository.save(client));
        Assertions.assertNotNull(exception.getMessage());
    }
}
