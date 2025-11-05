package com.yme.movementsservice.application.service;

import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Client;
import com.yme.movementsservice.application.input.port.ConsumerQueueService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Service methods for Consumer queue.
 */
@Service
@AllArgsConstructor
public class ConsumerQueueServiceImpl implements ConsumerQueueService {

    /**
     * Receive a message from queue.
     *
     * @param client message from queue.
     */
    @Override
    @RabbitListener(queues = {"client"})
    public void receiveMessage(Client client) {
        System.out.println("Cliente recibido---> " + client.getClientId());
    }
}
