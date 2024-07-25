package com.yme.clientservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Class for send to queue.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
public class AsyncClient implements Serializable {

    private String identification;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phoneNumber;
    private Long clientId;
    private String password;
    private Boolean status;
}
