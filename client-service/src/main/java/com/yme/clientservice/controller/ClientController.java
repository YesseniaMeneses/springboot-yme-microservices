package com.yme.clientservice.controller;

import com.yme.clientservice.domain.Client;
import com.yme.clientservice.entity.ClientEntity;
import com.yme.clientservice.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
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
    public ResponseEntity<Client> saveClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);
    }

    /** *
     * Update an existing client into db
     *
     * @param client
     * @return HttpStatus.OK and body with updated client
     */
    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client){
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    /**
     * Get all clients inserted in db
     *
     * @return HttpStatus.OK and body with a list of clients
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Search a client by clientId
     *
     * @param clientId
     * @return HttpStatus.OK and body with client object
     */
    @GetMapping("{clientId}")
    public ResponseEntity<Client> getClientByClientId(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientService.getClientByClientId(clientId));
    }

    /**
     * Delete a client from db
     *
     * @param clientId
     * @return HttpStatus.OK and Boolean
     */
    @DeleteMapping("{clientId}")
    public ResponseEntity<Boolean> deleteClientByClientId(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientService.deleteClientByClientId(clientId));
    }
}
