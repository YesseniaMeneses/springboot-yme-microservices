package com.yme.clientservice.service;

import com.yme.clientservice.domain.AsyncClient;

/**
 * Interface for queue methods.
 */
public interface ProducerQueueService {

    /**
     * Send a message to queue.
     *
     * @param client
     */
    void sendMessage(AsyncClient client);
}
