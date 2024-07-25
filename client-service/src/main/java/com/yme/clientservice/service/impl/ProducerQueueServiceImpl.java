package com.yme.clientservice.service.impl;

import com.yme.clientservice.domain.AsyncClient;
import com.yme.clientservice.service.ProducerQueueService;
import com.yme.clientservice.config.QueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Producer Queue methods.
 */
@Service
public class ProducerQueueServiceImpl implements ProducerQueueService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerQueueServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send message to queue.
     *
     * @param client
     */
    @Override
    public void sendMessage(AsyncClient client) {
        rabbitTemplate.convertAndSend(QueueConfig.CLIENT_KEY, QueueConfig.CLIENT_KEY, client);
    }
}
