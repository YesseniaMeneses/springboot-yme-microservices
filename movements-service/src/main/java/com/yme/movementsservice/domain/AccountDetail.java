package com.yme.movementsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Account detail to present in a report.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountDetail {
    private String clientName;
    private String accountNumber;
    private String accountType;
    private Boolean status;
    private BigDecimal initialBalance;
    private BigDecimal finalBalance;
}
