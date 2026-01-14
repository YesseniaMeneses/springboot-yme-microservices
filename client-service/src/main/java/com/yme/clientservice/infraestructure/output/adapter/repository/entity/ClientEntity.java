package com.yme.clientservice.infraestructure.output.adapter.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity to map to a db table.
 */
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
    @Column
    private Long clientId;
    @Column
    private String password;
    @Builder.Default
    private Boolean status = true;
}
