package com.yme.clientservice.controller;

import com.yme.clientservice.domain.AsyncClient;
import com.yme.clientservice.service.ProducerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/async/clientes")
public class AsyncClientController {

    private ProducerQueueService queueService;

    @Autowired
    public AsyncClientController(ProducerQueueService queueService) {
        this.queueService = queueService;
    }

    /** *
     * insert a client into queue
     *
     * @param client
     * @return info message
     */
    @PostMapping
    public ResponseEntity<String> saveClient(@RequestBody AsyncClient client){
        queueService.sendMessage(client);
        return ResponseEntity.ok("Cliente enviado");
    }
}
