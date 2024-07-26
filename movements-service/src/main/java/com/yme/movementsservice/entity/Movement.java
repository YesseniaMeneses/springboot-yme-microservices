package com.yme.movementsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity to map to a db table.
 * Movement information of an account.
 */
@Entity
@Table(name = "movements")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Column(nullable = false)
    @NotNull(message = "El tipo de movimiento no puede estar vacío.")
    private Character type;
    @Column(nullable = false)
    private BigDecimal amount;
    private BigDecimal availableBalance;
    private String description;
    @Transient
    private String accountNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number")
    @JsonIgnore
    private Account account;
}
