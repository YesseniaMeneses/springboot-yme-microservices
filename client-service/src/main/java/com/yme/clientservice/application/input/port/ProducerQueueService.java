package com.yme.clientservice.application.input.port;

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
