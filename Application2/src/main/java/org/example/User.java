package org.example;

import lombok.*;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private int id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String role;

    private Long timestamp;

    private Integer count;


}
