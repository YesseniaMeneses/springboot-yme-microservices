package com.yme.movementsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Final object to present in a report.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MovementsByAccount {
    private AccountDetail accountDetail;
    private List<MovementDetail> movements;
}
