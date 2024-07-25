package com.yme.movementsservice.controller;

import com.yme.movementsservice.domain.MovementsByAccount;
import com.yme.movementsservice.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/reportes")
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    /**
     * Get all movements for all accounts of a client.
     *
     * @param fecha date range
     * @param cliente clientId
     * @return HttpStatus.OK and body with a list of movementsByAccount.
     * @throws ParseException When date format is incorrect.
     */
   @GetMapping
    public ResponseEntity<List<MovementsByAccount>> getMovementsByClient(@RequestParam String fecha, @RequestParam Long cliente) throws ParseException {
        return ResponseEntity.ok(reportService.getMovementsByAccount(fecha, cliente));
    }
}
