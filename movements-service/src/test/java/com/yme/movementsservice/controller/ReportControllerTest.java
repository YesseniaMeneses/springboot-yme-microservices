package com.yme.movementsservice.controller;

import com.yme.movementsservice.BaseTest;
import com.yme.movementsservice.domain.MovementsByAccount;
import com.yme.movementsservice.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ReportControllerTest extends BaseTest {

    @InjectMocks private ReportController reportController;
    @Mock private ReportService reportService;

    @Test
    void getMovementsByClient() throws ParseException {
        List<MovementsByAccount> movements = new ArrayList<>();
        when(reportService.getMovementsByAccount(any(), any())).thenReturn(movements);

        ResponseEntity<List<MovementsByAccount>> response = reportController.getMovementsByClient(any(), any());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }
}
