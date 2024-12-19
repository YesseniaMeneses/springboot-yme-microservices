package com.yme.clientservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "La identificación no puede estar vacía.")
    private String identification;
    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;
    @NotBlank(message = "El género no puede estar vacío.")
    private String gender;
    @NotNull(message = "La edad no puede estar vacía.")
    @Min(value = 1, message = "Debe tener al menos 1 año de edad.")
    private Integer age;
    @NotBlank(message = "La dirección no puede estar vacía.")
    private String address;
    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Size(min = 10, max = 10, message = "El número de teléfono debe contener 10 números.")
    private String phoneNumber;
}
