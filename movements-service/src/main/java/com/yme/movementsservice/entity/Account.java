package com.yme.movementsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yme.movementsservice.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity to map to a db table.
 * Account information for a client.
 */
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "El número de cuenta no puede estar vacío.")
    private String accountNumber;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    private BigDecimal initialBalance = BigDecimal.valueOf(0.00);
    private BigDecimal finalBalance = BigDecimal.valueOf(0.00);
    private Boolean status = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;
}
