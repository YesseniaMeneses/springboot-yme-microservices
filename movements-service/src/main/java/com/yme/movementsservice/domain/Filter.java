package com.yme.movementsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for dateRange search.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Filter {
    private Long clientId;
    private String startDate;
    private String endDate;
}
