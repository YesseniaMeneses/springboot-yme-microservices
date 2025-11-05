package com.yme.movementsservice.application.input.port;

import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Client;

/**
 * Interface for Consumer queue.
 */
public interface ConsumerQueueService {

    /**
     * Receive a message from queue.
     *
     * @param client
     */
    void receiveMessage(Client client);
}
