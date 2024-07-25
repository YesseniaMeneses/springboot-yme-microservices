package com.yme.movementsservice.controller;

import com.yme.movementsservice.entity.Movement;
import com.yme.movementsservice.service.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movimientos")
@AllArgsConstructor
public class MovementController {

    private MovementService movementService;

    /**
     * Save a movement for one account.
     *
     * @param accountNumber
     * @param movement
     * @return HttpStatus.CREATED and body with object movement
     */
    @PostMapping("{accountNumber}")
    public ResponseEntity<Movement> saveMovement(@PathVariable("accountNumber") String accountNumber, @RequestBody Movement movement){
        return new ResponseEntity<>(movementService.saveMovement(accountNumber, movement), HttpStatus.CREATED);
    }

    /**
     * Get all existing movements.
     *
     * @return HttpStatus.OK and body with a list of movements
     */
    @GetMapping
    public ResponseEntity<List<Movement>> getAllMovements(){
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    /**
     * Get all movements for one account.
     *
     * @param accountNumber
     * @return HttpStatus.OK and body with a list of movements
     */
    @GetMapping("{accountNumber}")
    public ResponseEntity<List<Movement>> getMovementsByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(movementService.getMovementsByAccountNumber(accountNumber));
    }

    /**
     * Delete a movement.
     *
     * @param id
     * @return HttpStatus.OK and body with a Boolean value.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteMovementById(@PathVariable("id") Long id){
        return ResponseEntity.ok(movementService.deleteMovementById(id));
    }
}
