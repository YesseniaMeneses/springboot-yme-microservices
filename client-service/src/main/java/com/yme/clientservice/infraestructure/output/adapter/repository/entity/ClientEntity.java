package com.yme.clientservice.infraestructure.output.adapter.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity to map to a db table.
 */
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ClientEntity extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotNull(message = "El id del cliente no puede estar vacío.")
    private Long clientId;
    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String password;
    @Builder.Default
    private Boolean status = true;
}
