package com.yme.movementsservice.infraestructure.input.adapter.rest.impl;

import com.yme.movementsservice.application.input.port.AccountService;
import com.yme.movementsservice.infraestructure.output.adapter.repository.entity.Account;
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
@RequestMapping("api/cuentas")
@AllArgsConstructor
public class AccountController {
    
    private AccountService accountService;

    /**
     * Save an account for a client.
     *
     * @param clientId
     * @param account
     * @return HttpStatus.CREATED and body with object account
     */
    @PostMapping("{clientId}")
    public ResponseEntity<Mono<Account>> saveAccount(@PathVariable("clientId") Long clientId, @RequestBody Account account){
        return new ResponseEntity<>(accountService.saveAccount(clientId, account), HttpStatus.CREATED);
    }

    /**
     * Update an account for a client.
     *
     * @param clientId
     * @param account
     * @return HttpStatus.OK and body with object account
     */
    @PutMapping("{clientId}")
    public ResponseEntity<Mono<Account>> updateAccount(@PathVariable("clientId") Long clientId, @RequestBody Account account){
        return ResponseEntity.ok(accountService.updateAccount(clientId, account));
    }

    /**
     * Get all existing accounts.
     *
     * @return HttpStatus.OK and body with a list of accounts
     */
    @GetMapping
    public ResponseEntity<Mono<List<Account>>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Search an account by accountNumber.
     *
     * @param accountNumber
     * @return HttpStatus.OK and body with an account
     */
    @GetMapping("{accountNumber}")
    public ResponseEntity<Mono<Account>> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    /**
     * Delete an account.
     *
     * @param accountNumber
     * @return HttpStatus.OK and body with a Boolean value.
     */
    @DeleteMapping("{accountNumber}")
    public ResponseEntity<Mono<Boolean>> deleteAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.deleteAccountByAccountNumber(accountNumber));
    }
}
