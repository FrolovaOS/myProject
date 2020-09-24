package org.example;

import lombok.*;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String firstName;

    private String lastName;

    private Integer age;

    private String role;

}
