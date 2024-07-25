package com.yme.movementsservice.controller;

import com.yme.movementsservice.entity.Account;
import com.yme.movementsservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Account> saveAccount(@PathVariable("clientId") Long clientId, @RequestBody Account account){
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
    public ResponseEntity<Account> updateAccount(@PathVariable("clientId") Long clientId, @RequestBody Account account){
        return ResponseEntity.ok(accountService.updateAccount(clientId, account));
    }

    /**
     * Get all existing accounts.
     *
     * @return HttpStatus.OK and body with a list of accounts
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Search an account by accountNumber.
     *
     * @param accountNumber
     * @return HttpStatus.OK and body with an account
     */
    @GetMapping("{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    /**
     * Delete an account.
     *
     * @param accountNumber
     * @return HttpStatus.OK and body with a Boolean value.
     */
    @DeleteMapping("{accountNumber}")
    public ResponseEntity<Boolean> deleteAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.deleteAccountByAccountNumber(accountNumber));
    }
}
