package com.yme.movementsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity to map to a db table.
 * Client information.
 */
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String identification;
    @Column(nullable = false)
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private Long clientId;
    @Column(nullable = false)
    private String password;
    private Boolean status = true;
}
