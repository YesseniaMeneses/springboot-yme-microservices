package com.yme.clientservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for person info.
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Person {

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
}
