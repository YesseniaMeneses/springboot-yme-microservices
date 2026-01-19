package com.yme.clientservice.infraestructure.input.adapter.rest.impl;

import com.yme.clientservice.application.input.port.ClientService;
import com.yme.clientservice.domain.Client;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("v1/clientes")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    /** *
     * Insert a client into db
     *
     * @param client
     * @return HttpStatus.CREATED and body with object client
     */
    @PostMapping
    public ResponseEntity<Mono<Client>> saveClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);
    }

    /** *
     * Update an existing client into db
     *
     * @param client
     * @return HttpStatus.OK and body with updated client
     */
    @PutMapping
    public ResponseEntity<Mono<Client>> updateClient(@RequestBody Client client){
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    /**
     * Get all clients inserted in db
     *
     * @return HttpStatus.OK and body with a list of clients
     */
    @GetMapping
    public ResponseEntity<Mono<List<Client>>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Search a client by clientId
     *
     * @param clientId
     * @return HttpStatus.OK and body with client object
     */
    @GetMapping("{clientId}")
    public ResponseEntity<Mono<Client>> getClientByClientId(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientService.getClientByClientId(clientId));
    }

    /**
     * Delete a client from db
     *
     * @param clientId
     * @return HttpStatus.OK and Boolean
     */
    @DeleteMapping("{clientId}")
    public ResponseEntity<Mono<Boolean>> deleteClientByClientId(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientService.deleteClientByClientId(clientId));
    }
}
