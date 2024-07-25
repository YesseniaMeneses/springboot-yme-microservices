package com.yme.movementsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Movement detail to present in a report.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MovementDetail {
    private String date;
    private String type;
    private BigDecimal movement;
    private BigDecimal availableBalance;
}
